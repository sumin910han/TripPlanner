import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a single node in a graph that contains information about a location
 */
public class NodeAE implements NodeInterface {
    private String name; // name of this node's location
    private String description; // description of the location
    private List<EdgeInterface> edgesLeaving = new ArrayList<>(); // list of all edges starting at this node
    private List<EdgeInterface> edgesEntering = new ArrayList<>(); // list of all edges ending at this node


    /**
     * Creates a new Node object with a given name and description
     * @param name, the name given for this node
     * @param description, the description given for this node
     */
    public NodeAE(String name, String description) {
        this.name = name;
        this.description = description;
        // list of all edges ending at this node
        List<EdgeInterface> edges = new ArrayList<EdgeInterface>();
    }

    /**
     * Getter method for this node's name
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Getter method for this node's description
     * @return the description
     */
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<EdgeInterface> getEdges() {
        return null;
    }

    @Override
    public void addEdge(EdgeInterface edgeAdded) {

    }

    /**
     * Getter method for the edges leaving this node
     * @return a list of edges that start at this node
     */
    @Override
    public List<EdgeInterface> getEdgesLeaving() {
        return edgesLeaving;
    }

    /**
     * Getter method for the edges coming to this node
     * @return a list of edges that end at this node
     */
    @Override
    public List<EdgeInterface> getEdgesEntering() {
        return edgesEntering;
    }

    /**
     * Adds an edge to the list of edges starting at this node
     * @param edgeAdded, the edge being added to the list
     */
    @Override
    public void addEdgeLeaving(EdgeInterface edgeAdded) {
        edgesLeaving.add(edgeAdded);
    }

    /**
     * Adds an edge to the list of edges ending at this node
     * @param edgeAdded, the edge being added to the list
     */
    @Override
    public void addEdgeEntering(EdgeInterface edgeAdded) {
        edgesEntering.add(edgeAdded);
    }

    /**
     * Returns a string representation of this node in the form "name: description"
     * @return the string representation of this node
     */
    @Override
    public String toString() {
        return(name + ": " + description);
    }

    /**
     * Checks if a given Object is equal to this node by checking if it is a node with the same name,
     * description, and list of edges
     * @param o, Object being compared to this node
     * @return true if the Object is equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        // check that the given object is not null
        if (o != null) {
            // check that the object is a Node
            if (o instanceof NodeAE) {
                boolean sameName, sameDescription;
                // compare node names
                if (name == null) {
                    sameName = ((NodeAE) o).getName() == null;
                } else {
                    sameName = name.equals(((NodeAE) o).getName());
                }
                // compare node descriptions
                if (description == null) {
                    sameDescription = ((NodeAE) o).getDescription() == null;
                } else {
                    sameDescription = description.equals(((NodeAE) o).getDescription());
                }
                // return true if all values are the same
                if (sameName && sameDescription) {
                    return true;
                }
            }
        }
        // otherwise return false
        return false;
    }
}
