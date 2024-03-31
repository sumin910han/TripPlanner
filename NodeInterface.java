import java.util.List;

public interface NodeInterface {
    // public NodeInterface(String name, String description);
    public String getName();
    public String getDescription();
    // Note: the list of edges stored in a node should only contain edges that start at this node
    public List<EdgeInterface> getEdges();
    public void addEdge(EdgeInterface edgeAdded);
    public List<EdgeInterface> getEdgesEntering();
    public List<EdgeInterface> getEdgesLeaving();
    public void addEdgeEntering(EdgeInterface edgeAdded);
    public void addEdgeLeaving(EdgeInterface edgeAdded);
    @Override
    public String toString();
    public boolean equals(Object o);
}
