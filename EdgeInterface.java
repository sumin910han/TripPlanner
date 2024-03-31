public interface EdgeInterface {
    // public EdgeInterface(NodeInterface start, NodeInterface destination, int weight);
    public NodeInterface getStart();
    public NodeInterface getDestination();
    public Number getWeight();
    public double doubleValue();
    public void setStatus(boolean isOpen);
    // Note: an edge cannot be traversed when its open status is false
    public boolean getStatus();

    // Stores color for use by the frontend
    public void setColor(String hex);
    public String getColor();


}
