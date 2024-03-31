/**
 * This class models an edge between two nodes in a graph
 */
public class EdgeAE implements EdgeInterface {
    NodeInterface start; // node this edge starts at
    NodeInterface destination; // node this edge ends at
    Number weight; // weight of this edge
    boolean isOpen; // represents whether this edge can be traversed
    String color; // represents the color used to display the edge

    /**
     * Creates a new Edge object with a given start, destination, and weight
     *
     * @param start, start node for this edge
     * @param destination, destination node for this edge
     * @param weight, weight of this edge
     */
    public EdgeAE (NodeInterface start, NodeInterface destination, int weight) {
        this.start = start;
        this.destination = destination;
        this.weight = weight;
        isOpen = true;
        color = null;
    }

    /**
     * Getter method for this edge's start node
     * @return the start node
     */
    @Override
    public NodeInterface getStart() {
        return start;
    }

    /**
     * Getter method for this edge's destination node
     * @return the destination node
     */
    @Override
    public NodeInterface getDestination() {
        return destination;
    }

    /**
     * Getter method for this edge's weight
     * @return the weight
     */
    @Override
    public Number getWeight() {
        return weight;
    }

    @Override
    public double doubleValue() {
        return weight.doubleValue();
    }

    /**
     * Sets the status of this edge
     * @param isOpen, boolean used to change whether this edge can be traversed
     */
    @Override
    public void setStatus(boolean isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * Getter method for this edge's status
     * @return the open-status of this edge
     */
    @Override
    public boolean getStatus() {
        return isOpen;
    }


    /**
     * Sets the color of this edge
     * @param hex, string representation of the color being changed to
     */
    @Override
    public void setColor(String hex) {
        color = hex;
    }

    /**
     * Getter method for this edge's color
     * @return the color of this edge
     */
    @Override
    public String getColor() {
        return color;
    }

    /**
     * Checks if a given Object is equal to this edge by checking if it is an edge with the same name and description
     * for their start and destination nodes in addition to having the same weight, status, and color
     *
     * @param o, Object being compared to this edge
     * @return true if the Object is equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        // check that the given object is not null
        if (o != null) {
            // check that the object is an Edge
            if (o instanceof EdgeAE) {
                boolean sameStart, sameEnd, sameWeight, sameColor;
                // compare start node names and descriptions
                if (start == null) {
                    sameStart = (((EdgeAE) o).getStart() == null);
                } else {
                    boolean sameStartName, sameStartDesc;
                    if (start.getName() == null) {
                        sameStartName = ((EdgeAE) o).getStart().getName() == null;
                    } else {
                        sameStartName = start.getName().equals(((EdgeAE) o).getStart().getName());
                    }
                    if (start.getDescription() == null) {
                        sameStartDesc = ((EdgeAE) o).getStart().getDescription() == null;
                    } else {
                        sameStartDesc = start.getDescription().equals(((EdgeAE) o).getStart().getDescription());
                    }
                    sameStart = sameStartName && sameStartDesc;
                }
                // compare destination node names and descriptions
                if (destination == null) {
                    sameEnd = (((EdgeAE) o).getDestination() == null);
                } else {
                    boolean sameEndName, sameEndDesc;
                    if (destination.getName() == null) {
                        sameEndName = ((EdgeAE) o).getDestination().getName() == null;
                    } else {
                        sameEndName = destination.getName().equals(((EdgeAE) o).getDestination().getName());
                    }
                    if (destination.getDescription() == null) {
                        sameEndDesc = ((EdgeAE) o).getDestination().getDescription() == null;
                    } else {
                        sameEndDesc = destination.getDescription().equals(((EdgeAE) o).getDestination().getDescription());
                    }
                    sameEnd = sameEndName && sameEndDesc;
                }
                // compare edge weights
                if (weight == null) {
                    sameWeight = (((EdgeAE) o).getWeight() == null);
                } else {
                    sameWeight = weight.equals(((EdgeAE) o).getWeight());
                }
                // compare edge colors
                if (color == null) {
                    sameColor = ((EdgeAE) o).getColor() == null;
                } else {
                    sameColor = color.equals(((EdgeAE) o).getColor());
                }
                // return true if all values are the same
                if (sameStart && sameEnd && sameWeight && sameColor && isOpen == ((EdgeAE) o).getStatus()){
                    return true;
                }
            }
        }
        // otherwise return false
        return false;
    }
}
