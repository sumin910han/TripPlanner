import java.util.Hashtable;
import java.util.List;

public interface AdvancedGraphInterface {
    // Methods from GraphADT.java
    public boolean insertNode(NodeInterface data);
    public boolean containsNode(NodeInterface data);
    public boolean insertEdge(NodeInterface pred, NodeInterface succ, EdgeInterface edge);
    public boolean containsEdge(NodeInterface pred, NodeInterface succ);
    public EdgeInterface getEdge(NodeInterface pred, NodeInterface succ); // throw NoSuchElementException() if no edge is found
    public Hashtable<String, NodeInterface> getNodes();

    // Exclusive funtionalities
    public List<NodeInterface> shortestPathData(NodeInterface start, NodeInterface end);
    public double shortestPathCost(NodeInterface start, NodeInterface end);
    public void toggleStatus(NodeInterface pred, NodeInterface succ);
    public List<List<NodeInterface>> shortestTripData(List<NodeInterface> nodeToGo);
    public List<EdgeInterface> minimumSpanningTree();
}
