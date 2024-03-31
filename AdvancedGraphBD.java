import java.util.*;

/**
 * Placeholder class for the AE part. Hardcoded with minimum functionalities that returns specific values, that simulates
 * the actual implementation
 *
 * @param <NodeType> type of node used
 * @param <EdgeType> type of edges used
 */
public class AdvancedGraphBD<NodeType, EdgeType extends Number> implements AdvancedGraphInterface {
    int nodeCount; // number of nodes
    int edgeCount;
    EdgeType weight;
    protected Hashtable<String, NodeType> nodes; // Hashtable from BaseGraph.java.

    /**
     * Constructor for hardcoded class
     * @param weight defines the weight as a double
     */
    public AdvancedGraphBD(EdgeType weight){
        nodeCount = 0;
        edgeCount = 0;
        this.weight = weight;
        nodes = new Hashtable();
    }

    /**
     * Inserts when there is "good node" and adds node count for testing purposes.
     * @param data is the data item stored in the new node
     * @return true if is a good node, false otherwise
     */
    @Override
    public boolean insertNode(NodeType data) {
        if(data!=null || ((NodeInterfaceBD)data).getName().contains("good node")){
            nodeCount++;
            return true;
        }
        return false;
    }

    /**
     * Inserts when there is "good node" and adds node count for testing purposes.
     *
     * @param data is the data item stored in the node to be removed
     * @return true if its a good node, false otherwise
     */
    @Override
    public boolean removeNode(NodeType data) {
        if(data!=null || ((NodeInterfaceBD)data).getName().contains("good node")){
            nodeCount--;

            return true;
        }
        return false;
    }

    /**
     * Hardcoded to return true if its a good node
     * @param data the node contents to check for
     * @return true if its a good node, false otherwise
     */
    @Override
    public boolean containsNode(NodeType data) {
        if(data!=null || data.equals("good node")){
            return true;
        }
        return false;
    }

    /**
     * Gets the number of nodes to test for insertion and deletion through backend
     * @return
     */
    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    /**
     * Inserts edge when both nodes are good nodes. Replicates the actual insertion code
     * @param pred is the data item contained in the new edge's predecesor node
     * @param succ is the data item contained in the new edge's successor node
     * @param weight is the non-negative data item stored in the new edge
     * @return true if good node. false otherwise
     */
    @Override
    public boolean insertEdge(NodeType pred, NodeType succ, EdgeType weight) {
        if(pred!=null && ((String) pred).contains("good node") && succ!=null && ((String)succ).contains("good node")){
            edgeCount++;
            return true;
        }
        return false;
    }

    /**
     * Removes edge when both nodes are good and decrements edgeCount. Replicates the actual removal process.
     * @param pred the data item contained in the source node for the edge
     * @param succ the data item contained in the target node for the edge
     * @return rue if good node. false otherwise
     */
    @Override
    public boolean removeEdge(NodeType pred, NodeType succ) {
        if(pred!=null && ((String) pred).contains("good node") && succ!=null && ((String)succ).contains("good node")){
            edgeCount--;
            return true;
        }
        return false;
    }

    /**
     * Tests if contains edge
     * @param pred the data item contained in the source node for the edge
     * @param succ the data item contained in the target node for the edge
     * @return true if good node, false otherwise
     */
    @Override
    public boolean containsEdge(NodeType pred, NodeType succ) {
        if(pred!=null && ((String) pred).contains("good node") && succ!=null && ((String)succ).contains("good node")){
            return true;
        }
        return false;
    }

    /**
     * Gets the edge. Returns a double value for the sake of testing it.
     *
     * @param pred the data item contained in the source node for the edge
     * @param succ the data item contained in the target node for the edge
     * @return edgetype value
     */
    @Override
    public EdgeType getEdge(NodeType pred, NodeType succ) {
        if(pred!=null && ((String) pred).contains("good node") && succ!=null && ((String)succ).contains("good node")){
            return weight;
        }
        return null;
    }

    /**
     * Gets the number of edges to test for the insertion and removal processes.
     * @return he number of edges
     */
    @Override
    public int getEdgeCount() {
        return edgeCount;
    }

    /**
     * Returns a list of nodes to replicate the actual code that provides a path.
     * Tests if the path is correctly handled by the BD code to ensure the FD uses it properly.
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return a linked list of hardcoded nodes
     */
    @Override
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        if(start!=null && ((NodeInterfaceBD) start).getName().contains("good node") && end!=null && ((NodeInterfaceBD)end).getName().contains("good node")){
            return new LinkedList<NodeType>((Collection<? extends NodeType>) Arrays.asList("node1", "node2", "node3"));
        }
        return null;
    }

    /**
     * Returns a fixed cost value if the user input is correct to test if the value is being handled properly by backend
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return 3 if good node, 0 otherwise
     */
    @Override
    public double shortestPathCost(NodeType start, NodeType end) {
        if(start!=null && ((NodeInterfaceBD) start).getName().contains("good node") && end!=null && ((NodeInterfaceBD)end).getName().contains("good node")){
            return 3;
        }
        return 0;
    }

    /**
     * Hard coded to replicate how edges can be toggled on and off to be displayed on screen.
     * @param pred the node leading to the other
     * @param succ the node that branches from the first.
     */
    @Override
    public void toggleStatus(NodeType pred, NodeType succ) {
        if(pred!=null && ((String) pred).contains("good node") && succ!=null && ((String)succ).contains("good node")){
            System.out.println("toggled");;
        }
        throw new NoSuchElementException("pred and succ does not exist");
    }

    /**
     * Hardcoded to return a path of nodes if the list is not empty.
     * @param nodeToGo list of nodes of the journey
     * @return the list of shortest paths of the journey
     */
    @Override
    public List<List<NodeType>> shortestTripData(List<NodeType> nodeToGo) {
        if(nodeToGo!=null){
            List<List<NodeType>> result = new LinkedList<>();
            result.add((List<NodeType>) Arrays.asList("node1", "node2", "node3"));
            result.add((List<NodeType>) Arrays.asList("node4", "node5", "node6"));
            return result;
        }
        return null;
    }


    @Override
    public List<EdgeType> minimumSpanningTree() {
        return null;
    }

    /**
     * gets the hashtable of nodes from Basegraph.java (Hardcoded in this placeholder class) for the FD and BD to use.
     * @return
     */
    public Hashtable<String, NodeType> getNodes(){
        return nodes;
    }

}
