import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class glues the AE, DW and FD implementations together.
 */
public class TripPlannerBackendBD implements TripPlannerBackendBDInterface {

    GraphReaderInterface reader; // reader object that will return a list of nodes to be placed into the hashtable by AE
    AdvancedGraphInterface graph; // AE's graph that contains all the driving functionalities of the program.
    List<NodeInterface> currentPath; // the current shortest path returned by the AE code. Can be reused for multiple scenarios by FD.
    Hashtable<String, NodeInterface> nodes;
    /**
     * Constructor to initialize all fields
     * @param reader the reader reference to read from a file
     * @param graph the advanced graph reference to insert, find, delete etc.
     */
    public TripPlannerBackendBD(GraphReaderInterface reader, AdvancedGraphInterface graph){
        this.reader = reader;
        this.graph = graph;
        this.nodes = graph.getNodes();
    }

    /**
     * Loads data by calling the reader class' method, and adds them into the graph for every node.
     * @param filename the name of the file user intends to load
     * @throws FileNotFoundException if File is invalid
     */
    @Override
    public void loadData(String filename) throws FileNotFoundException {
        List<NodeInterface> nodes = reader.loadFromFile(filename);
        for(NodeInterface node: nodes){
            addOneData(node);
        }

    }

    /**
     * Adds a node into the graph
     * @param nodeToAdd the node that user intends to add
     */
    @Override
    public void addOneData(NodeInterface nodeToAdd) {
        graph.insertNode(nodeToAdd);
    }

    /**
     * A list of paths derived from the user input.
     * @param input a string of node names seperated by ","
     * @return a list of paths indicating the shortes path between A->D and D-> A
     * @throws NoSuchElementException if element does not exist
     */
    @Override
    public List<List<NodeInterface>> findShortestTrips(String input) throws NoSuchElementException {
        String[] userInput = input.split(",");
        List<NodeInterface> nodePath = new LinkedList<>();
        for(String s: userInput){
            nodePath.add(nodes.get(s.trim()));
        }
        return graph.shortestTripData(nodePath);
    }

    /**
     * Removes the current graph.
     */
    @Override
    public void removeTree() {
        graph= null;
    }

    /**
     * Finds a single shortest path between nodes start to end.
     * @param startNodeName the start node
     * @param endNodeName the end node
     * @return a list of nodes respective to the path.
     */
    @Override
    public List<NodeInterface> findShortestPath(String startNodeName, String endNodeName) {
        currentPath =  graph.shortestPathData(nodes.get(startNodeName), nodes.get(endNodeName));
        return currentPath;
    }

    /**
     * Finds the cost of the shortest path.
     * @param startNodeName the start node
     * @param endNodeName the end node
     * @return a cost number associated with the weight of nodes in the path.
     */
    @Override
    public double shortestPathCost(String startNodeName, String  endNodeName) {
        return graph.shortestPathCost(nodes.get(startNodeName), nodes.get(endNodeName));
    }

    /**
     *  Gets the total number of nodes that exist in the grapg.
     * @return the number of nodes
     */
    @Override
    public int getTotalPlaces() {
        return nodes.size();
    }

    /**
     * Gets the minimum spanning tree of nodes.
     * @return a list of edges that make up the MST
     */
    @Override
    public List<EdgeInterface> getMST() {
        return graph.minimumSpanningTree();
    }

    /**
     * Toggles if a certain edge is usable or not.
     * @param input a string containing the node names.
     */
    @Override
    public void toggleEdge(String[] input) {
        NodeInterface start = nodes.get(input[0]);
        NodeInterface end = nodes.get(input[1]);
        graph.toggleStatus(start, end);
    }

    /**
     * Gets a hashtable of all the nodes that exist in the graph
     * @return hashtable of nodes
     */
    public Hashtable<String, NodeInterface> getNodes(){
        return nodes;
    }

}
