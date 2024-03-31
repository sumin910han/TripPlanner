import java.util.ArrayList;
import java.util.List;

public class NodeInterfaceBD implements NodeInterface {
  private String name;
  private String description;
  private List<EdgeInterface> edges;
  private List<EdgeInterface> edgesLeaving = new ArrayList<>(); // list of all edges starting at this node
  private List<EdgeInterface> edgesEntering = new ArrayList<>(); // list of all edges ending at this node

  public NodeInterfaceBD(String name, String description) {
    this.name = name;
    this.description = description;
    this.edges = new ArrayList<>();
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getDescription() {
    return this.description;
  }

  @Override
  public List<EdgeInterface> getEdges() {
    return this.edges;
  }

  @Override
  public void addEdge(EdgeInterface edgeAdded) {
    if (edgeAdded.getStart().equals(this)) {
      this.edges.add(edgeAdded);
    }
  }

  @Override
  public List<EdgeInterface> getEdgesEntering() {
    return edgesEntering;
  }

  @Override
  public List<EdgeInterface> getEdgesLeaving() {
    return edgesLeaving;
  }

  @Override
  public void addEdgeEntering(EdgeInterface edgeAdded) {
    edgesEntering.add(edgeAdded);
  }

  @Override
  public void addEdgeLeaving(EdgeInterface edgeAdded) {
    edgesLeaving.add(edgeAdded);
  }

  @Override
  public String toString() {
    return this.name + ": " + this.description;
  }

  @Override
  public boolean equals(Object o) {
    // check that the given object is not null
    if (o != null) {
      // check that the object is a Node
      if (o instanceof NodeInterfaceBD) {
        boolean sameName, sameDescription;
        // compare node names
        if (name == null) {
          sameName = ((NodeInterfaceBD) o).getName() == null;
        } else {
          sameName = name.equals(((NodeInterfaceBD) o).getName());
        }
        // compare node descriptions
        if (description == null) {
          sameDescription = ((NodeInterfaceBD) o).getDescription() == null;
        } else {
          sameDescription = description.equals(((NodeInterfaceBD) o).getDescription());
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
