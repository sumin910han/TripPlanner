import java.io.FileNotFoundException;
import java.util.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;



public class DataWranglerTests {

  /**
   * Test node class
   * Make sure node constructor and methods are working
   */
  @Test
  public void testNodes() {
    Node a = new Node("Memorial Union", "This place is the student union");
    assertEquals("Memorial Union", a.getName());
    assertTrue(a.getDescription().contains("student union"));

  }

  /**
   * Test edges of nodes
   * Create a few nodes and edges and test that the edges are added and have the proper information
   */
  @Test
  public void testEdges() {
    Node a = new Node("Memorial Union", "This place is the student union");
    Node b = new Node("Gordon", "Dining Hall");
    Node c = new Node("Kohl Center", "Basketball arena");

    Edge edgeAB = new Edge(a,b,5);
    Edge edgeBC = new Edge(b,c,3);
    Edge edgeAC = new Edge(a,c,2);

    //check that improper edge isn't added to node
    a.addEdge(edgeBC);
    assertTrue(a.getEdges().isEmpty());

    a.addEdge(edgeAB);
    a.addEdge(edgeAC);
    b.addEdge(edgeBC);

    assertTrue(a.getEdges().size() == 2);
    assertTrue(b.getEdges().size() == 1);
    assertTrue(c.getEdges().isEmpty());

  }

  /**
   * Test graph reader
   * Check that the right number of nodes are being returned and that one of the nodes from the file exists
   */
  @Test
  public void testGraphReaderNodes() throws FileNotFoundException {
    GraphReader temp = new GraphReader();
    List<NodeInterface> nodes = temp.loadFromFile("example.dot");

    assertTrue(nodes.size()==6);
    assertTrue(nodes.get(0).getDescription().equals("Memorial Union"));

  }

  /**
   * Test graph reader
   * Check that the correct edges are being added to the node from the file
   */
  @Test
  public void testGraphReaderEdges() throws FileNotFoundException {
    GraphReader temp = new GraphReader();
    List<NodeInterface> nodes = temp.loadFromFile("example.dot");

    assertTrue(nodes.get(0).getEdges().size() == 2);
    assertEquals(5,nodes.get(0).getEdges().get(0).getWeight());
    assertEquals(7,nodes.get(0).getEdges().get(1).getWeight());

  }

  /**
   * Test graph reader
   * Check that all the edge information is being loaded correcrly from the file
   */
  @Test
  public void testGraphReaderEdgeInfo() throws FileNotFoundException{
    GraphReader temp = new GraphReader();
    List<NodeInterface> nodes = temp.loadFromFile("example.dot");
    assertEquals(new Node("A", "Memorial Union"),nodes.get(0).getEdges().get(0).getStart());
    assertEquals(new Node("B", "Nic Recreation"),nodes.get(0).getEdges().get(0).getDestination());
    assertEquals(5,nodes.get(0).getEdges().get(0).getWeight());
    assertTrue(nodes.get(0).getEdges().get(0).getStatus());
    assertEquals("#000000",nodes.get(0).getEdges().get(0).getColor());
  }

}
