// --== CS400 File Header Information ==--
// Name: Aditya Anandan
// Email: anandan2@wisc.edu
// Group and Team: BK blue
// Group TA: Naman Gupta
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Set;

public class AdvancedGraphAE implements AdvancedGraphInterface{

    protected class SearchNode implements Comparable<SearchNode> {
        public NodeInterface node;
        public double cost;
        public SearchNode predecessor;
        public SearchNode(NodeInterface start, double cost, SearchNode predecessor) {
            this.node = start;
            this.cost = cost;
            this.predecessor = predecessor;
        }
        public int compareTo(SearchNode other) {
            if( cost > other.cost ) return +1;
            if( cost < other.cost ) return -1;
            return 0;
        }
    }

	


    public class PrimNode implements Comparable<PrimNode>{
        EdgeInterface edge;
        NodeInterface node;
        double cost;
        public PrimNode(EdgeInterface edge, NodeInterface node, double cost) {
            this.edge = edge;
            this.node = node;
            this.cost = cost;
        }
        public int compareTo(PrimNode other) {
            if( cost > other.cost ) return +1;
            if( cost < other.cost ) return -1;
            return 0;
        }
    }

    // nodes instance variable
    protected Hashtable<String,NodeInterface> nodes = new Hashtable();

	public Hashtable<String, NodeInterface> getNodes(){
		return nodes;
	}

    // Methods derived from GraphADT.java
    public boolean insertNode(NodeInterface data) {
        if(nodes.containsKey(data.getName())) return false; // throws NPE when data's null
        nodes.put(data.getName(), data);
        return true;
    }

    public boolean containsNode(NodeInterface data) {
        return nodes.containsKey(data.getName()) ;
    }

    public boolean insertEdge(NodeInterface pred, NodeInterface succ, EdgeInterface edge) {
        if(pred == null || succ == null) return false;
        pred.addEdgeLeaving(edge);
        succ.addEdgeEntering(edge);
        return true;
    }

    public boolean containsEdge(NodeInterface pred, NodeInterface succ) {
        try { getEdge(pred,succ); return true; }
        catch(NoSuchElementException e) { return false; }
    }

    public EdgeInterface getEdge(NodeInterface pred, NodeInterface succ) {
        List<EdgeInterface> edgesLeaving = pred.getEdgesLeaving();
        for (EdgeInterface edge: edgesLeaving) {
            if (edge.getDestination().equals(succ)) {
                return edge;
            }
        }
        throw new NoSuchElementException("There is no edge from " + pred.getName() + " to " + succ.getName());
    }

    protected SearchNode computeShortestPath(NodeInterface start, NodeInterface end) {
        // If the start data or the end data do not correspond to a graph node then a NoSuchElementException is thrown.
        if (!this.containsNode(start)) {
            throw new NoSuchElementException("The start data does not correspond to a graph node");
        }
        if (!this.containsNode(end)) {
            throw new NoSuchElementException("The end data does not correspond to a graph node");
        }

        PriorityQueue<SearchNode> pq = new PriorityQueue<SearchNode>();
        Hashtable<String, SearchNode> visited = new Hashtable<String, SearchNode>();

        // Create a start SearchNode
        SearchNode startNode = new SearchNode(start, 0.0, null);
        pq.add(startNode);

        while (true) {
            if (pq.isEmpty()) {
                break;
            }
            SearchNode currentSearchNode = pq.poll();

            if (visited.containsKey(currentSearchNode.node.getName())) {
                continue;
            }
            visited.put(currentSearchNode.node.getName(), currentSearchNode);
            NodeInterface currentNode = currentSearchNode.node;
            List<EdgeInterface> edgesLeaving = currentNode.getEdgesLeaving();
            for (EdgeInterface edge : edgesLeaving) {
                NodeInterface nextNode = edge.getDestination();
                if (!edge.getStatus()) {
                    continue;
                }
                if (visited.containsKey(nextNode.getName())) {
                    continue;
                }
                SearchNode newNodePath = new SearchNode(nextNode, currentSearchNode.cost + edge.getWeight().doubleValue(), currentSearchNode);
                pq.add(newNodePath);
            }
        }

        if (!visited.containsKey(end.getName())) {
            throw new NoSuchElementException("There is no path from start to end");
        }
        else {
            return visited.get(end.getName());
        }
    }

    public List<NodeInterface> shortestPathData(NodeInterface start, NodeInterface end) {
        SearchNode pathToDo = computeShortestPath(start, end);
        LinkedList<NodeInterface> path = new LinkedList<NodeInterface>();
        while (pathToDo != null) {
            path.addFirst(pathToDo.node);
            pathToDo = pathToDo.predecessor;
        }
        return path;
    }

    public double shortestPathCost(NodeInterface start, NodeInterface end) {
        SearchNode pathToDo = computeShortestPath(start, end);
        return pathToDo.cost;
    }

    // Methods exclusive to this interfaces
    public void toggleStatus(NodeInterface pred, NodeInterface succ) {
        EdgeInterface edge = getEdge(pred, succ);
        edge.setStatus(!edge.getStatus());
    }

    public List<List<NodeInterface>> shortestTripData(List<NodeInterface> nodeToGo) {
        List<List<NodeInterface>> shortestPathList = new ArrayList<List<NodeInterface>>();
        ArrayList<NodeInterface> destinations = new ArrayList<NodeInterface>();
        for (NodeInterface node: nodeToGo) {
            destinations.add(node);
        }
        for (int i = 0; i < nodeToGo.size() - 1; ++i) {
            shortestPathList.add(shortestPathData(destinations.get(i), destinations.get(i+1)));
        }
        return shortestPathList;
    }

    protected List<EdgeInterface> mstHelper(NodeInterface start, Hashtable<String, Number> visited) {
        PriorityQueue<PrimNode> pq = new PriorityQueue<PrimNode>();
        List<EdgeInterface> newMST = new ArrayList<EdgeInterface>();
        PrimNode startEdge = new PrimNode(null, start, 0.0);
        pq.add(startEdge);
        while(!pq.isEmpty()) {
            PrimNode currentEdge = pq.poll();
            NodeInterface currentNode = currentEdge.node;
            if (visited.containsKey(currentNode.getName())) {
                continue;
            }
            visited.put(currentNode.getName(), currentEdge.cost);
            if (currentEdge.edge != null)
                newMST.add(currentEdge.edge);
            List<EdgeInterface> edgesLeaving = currentNode.getEdgesLeaving();
            for (EdgeInterface e : edgesLeaving) {
                NodeInterface other = e.getDestination();
                if (visited.containsKey(other.getName())) {
                    continue;
                }
                if (!e.getStatus()) {
                    continue;
                }
                PrimNode newEdge = new PrimNode(e, other, e.getWeight().doubleValue());
                pq.add(newEdge);
            }
            List<EdgeInterface> edgesEntering = currentNode.getEdgesEntering();
            for (EdgeInterface e : edgesEntering) {
                NodeInterface other = e.getStart();
                if (visited.containsKey(other.getName())) {
                    continue;
                }
                if (!e.getStatus()) {
                    continue;
                }
                PrimNode newEdge = new PrimNode(e, other, e.getWeight().doubleValue());
                pq.add(newEdge);
            }
        }
        return newMST;
    }

    public List<EdgeInterface> minimumSpanningTree() {
        List<EdgeInterface> mst = new ArrayList<EdgeInterface>();
        Hashtable<String, Number> visited = new Hashtable<String, Number>();
        Set<String> nodeKeys = nodes.keySet();
        for (String node: nodeKeys) {
            if (!visited.containsKey(node)) {
                ArrayList<EdgeInterface> newMST = (ArrayList<EdgeInterface>) mstHelper(nodes.get(node), visited);
                for (EdgeInterface e: newMST) {
                    mst.add(e);
                }
            }
        }

        return mst;
    }

}
