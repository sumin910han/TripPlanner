import java.util.Hashtable;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

public interface TripPlannerBackendBDInterface {

//AdvancedGraphInterface tree;
//List<NodeType> currentPath;

        // public TripPlannerBackendInterfaceXX(GraphReaderInterface reader, AdvancedGraphInterface graph);

        public void loadData(String filename) throws FileNotFoundException;

        public void addOneData(NodeInterface nodeToAdd); // Call DW constructor to make a node.
        // Trip plan will call findShortestPath twice, from A-D, then D-A for return trip. Will have to call search(A-D) then pass into shortestTripData
        public List<List<NodeInterface>> findShortestTrips(String input) throws NoSuchElementException;
        public void removeTree(); // Point tree reference to null

        public List<NodeInterface> findShortestPath(String startNodeName, String endNodeName);
        public double shortestPathCost (String startNodeName, String endNodeName);
        public int getTotalPlaces();
        // Returns the entire tree for visualization
        public List<EdgeInterface> getMST();
        // String[0] : Pred node String [1] succ node
        public void toggleEdge(String[] input);

		public Hashtable<String, NodeInterface> getNodes();
}
