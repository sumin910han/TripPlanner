// The Assertions class that we import from here includes assertion methods like assertEquals()
// which we will used in test1000Inserts().
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes.  This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType,EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph.  The final node in this path is stored in it's node
     * field.  The total cost of this path is stored in its cost field.  And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in it's node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;
        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }
        public int compareTo(SearchNode other) {
            if( cost > other.cost ) return +1;
            if( cost < other.cost ) return -1;
            return 0;
        }
    }



    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations.  The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *         or when either start or end data do not correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        // TODO: implement in step 6

        // set each node to be non-visited by making a visited hashtable
        Hashtable<NodeType, Node> visited = new Hashtable<>();
        // get hashtable containing all nodes
        Hashtable<NodeType, Node> ht = this.nodes;
        // pq which contains searchNodes representing a path
        PriorityQueue<SearchNode> pq = new PriorityQueue<SearchNode>();

        // starting node to place into the priority queue (minimum)
        SearchNode st = new SearchNode(ht.get(start), 0, null);
        pq.add(st);
        // current node which keeps track of minimum path
        SearchNode current = null;
        // main loop which performs dijkstra's algorithm
        while (!pq.isEmpty()){
            // remove minimum element from pq and store data of that node
            SearchNode temp = pq.peek();
            SearchNode pred = temp.predecessor;
            double cost = temp.cost;
            pq.remove(pq.peek());
            // check if node is unvisited
            if (!visited.contains(temp.node)){
                // make node visited
                visited.put(temp.node.data, temp.node);
                // reassign data
                temp.predecessor = pred;
                temp.cost = cost;
                //obtain all edges leaving that node
                List<Edge> edges = temp.node.edgesLeaving;
                // check if each edge is valid (has not been visited)
                for (Edge e : edges){
                    Node suc = e.successor;
                    if (!visited.contains(suc)){
                        // create new SearchNode with new "path" and cost of the path
                        SearchNode toAdd = new SearchNode(suc, cost + e.data.doubleValue(), temp);
                        // add new search node to the pq
                        pq.add(toAdd);
                        // check whether this is the shortest path from start to end
                        if (suc.data.equals(end) && (current == null || toAdd.cost <= current.cost)){
                            // reassign current if so
                            current = toAdd;
                        }
                    }
                }

            }
            // check if end node has been reached and return the shortest path if so
            if (visited.contains(ht.get(end)) && current != null){
                return current;
            }

        }



        // deal with edge case regarding start and end node being the same
        if (start.equals(end)){
            return new SearchNode(ht.get(start), 0, null);
        }

        // throw NoSuchElementException() if no path found
        throw new NoSuchElementException("No path is found");
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value.  This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path.  This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        // set up try/catch to throw a new exception
        try{
            // temp now represents the final searchNode in this path
            SearchNode temp = computeShortestPath(start, end);
            // initialize array of NodeTypes to represent the path
            List<NodeType> lst = new ArrayList<NodeType>();
            // loop to go back the path
            while (temp != null){
                // node is the current data of temp
                NodeType node = temp.node.data;
                // add current node to the start of the list since path is back to front
                lst.add(0, node);
                // move back the path by setting temp to be the predecessor
                temp = temp.predecessor;
            }
            // return the list
            return lst;
        } catch (Exception e){
            // throw new NoSuchElementException
            throw new NoSuchElementException("No path can be found between the two nodes");
        }

    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data.  This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        // set up try/catch to throw a new NoSuchElementException
        try{
            // cost of the last searchNode represents the cost of the path as a whole
            return computeShortestPath(start, end).cost;
        } catch (Exception e){
            // throw NoSuchElementException if no path can be found
            throw new NoSuchElementException("No path found");
        }

    }





}
