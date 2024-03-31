import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GraphReader implements GraphReaderInterface{
  /**
   * Load nodes from dot file
   * @param filename
   * @return a list of the nodes
   * @throws FileNotFoundException
   */
  public List<NodeInterface> loadFromFile(String filename) throws FileNotFoundException {
		System.out.println("Parsing nodes from " + filename);
    List<NodeInterface> nodes = new ArrayList<>();
    File file = new File(filename);
    Scanner scanner = new Scanner(file);
    scanner.nextLine(); // skip the first line, which specifies that this is a digraph
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();
      if (line.isEmpty() || line.startsWith("}")) {
        break; // reached the end of the graph
      }
      else if (line.startsWith("//")) {
        continue; // skip comments
      }
      else if (line.contains("->")) {
        // this is an edge definition, ignore for now
        continue;
      }
      else {
        // this is a node definition, parse it
        String[] parts = line.split("\\[");
        String nodeName = parts[0].trim();
        String nodeLabel = parts[1].replaceAll("[\"{}]", "").trim();
        nodeLabel = nodeLabel.substring(6,nodeLabel.length()-2);
        NodeInterface node = new Node(nodeName, nodeLabel);
        nodes.add(node);
      }
    }

    scanner.close();
    getEdgesFromFile(filename,nodes);
    return nodes;
  }

  /**
   * helper method to get add the edges to the nodes from the dot file
   * @param filename - name of dot file
   * @param nodes - List of nodes
   * @throws FileNotFoundException if file not found
   */
  private void getEdgesFromFile(String filename, List<NodeInterface> nodes) throws FileNotFoundException {
    Scanner scanner = new Scanner(new File(filename));
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();
      if (line.startsWith("//") || line.isEmpty() || !line.contains("->")) {
        // Ignore comments and empty lines
        continue;
      }
      String[] tokens = line.split("\\s+->\\s+");
      String startNodeName = tokens[0];
      String endNodeAndWeight = tokens[1];
      String[] endNodeAndWeightTokens = endNodeAndWeight.split("weight = \"");
      String endNodeName = endNodeAndWeightTokens[0].trim();
      endNodeName = endNodeName.substring(0,endNodeName.indexOf("[")).trim();
      int weight = Integer.parseInt(endNodeAndWeightTokens[1].substring(0, endNodeAndWeightTokens[1].length() - 3));
      // use the addEdge method in the n`ode to automatically add if the start node = the node
      NodeInterface startNode = null, endNode = null;
      for (NodeInterface node : nodes) {
        if (node.getName().equals(startNodeName)) {
          startNode = node;
        }
        if (node.getName().equals(endNodeName)) {
          endNode = node;
        }
        if (startNode != null && endNode != null) {
          break;
        }
      }
      // Create and add the edge to the start node
      EdgeInterface edge = new Edge(startNode, endNode, weight);
      startNode.addEdgeLeaving(edge);
      endNode.addEdgeEntering(edge);
    }
    scanner.close();
  }

}
