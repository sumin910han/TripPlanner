import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class contains JUNIT5 testers for the backenddeveloper. Tests the implementation of the backend that glues together all the roles,
 * and ensures the data fetched from the AE and DW is handled correctly so FD can display the results accurately.
 */
public class BackendDeveloperTests {
        GraphReaderInterfaceBD reader;
        AdvancedGraphBD<NodeInterfaceBD, EdgeInterfaceBD> graph;
        TripPlannerBackendBD<NodeInterfaceBD, EdgeInterfaceBD> tripPlanner;

        @BeforeEach
        void setup() {
            // Initializing all objects before testing
            reader = new GraphReaderInterfaceBD();
            graph = new AdvancedGraphBD<>(null);
            tripPlanner = new TripPlannerBackendBD<>(reader, graph);
        }

    /**
     * Tests if the program loads a file correctly given a good input, and throws an exception otherwise
     */
    @Test
    void testLoadData() {
            assertThrows(FileNotFoundException.class, () -> tripPlanner.loadData("bad file"));
            assertDoesNotThrow(()-> tripPlanner.loadData("good file"));

            // Test if the file is loaded correctly. and the object is initialised correctly. total places should return 3
            assertEquals(3, tripPlanner.getTotalPlaces());
        }

    /**
     * Tests if the code calls the advancedgraph.insert properly.
     */
    @Test
    void testAddOneData() {
            NodeInterfaceBD node1 = new NodeInterfaceBD("Node1", "node one");
            tripPlanner.addOneData(node1);
            assertEquals(1, tripPlanner.getTotalPlaces());
        }

    /**
     * Tests if the code calls the advancedgraph.remove properly
     */
    @Test
    void testRemoveOneData() {
            NodeInterfaceBD node1 = new NodeInterfaceBD("Node1", "node one");
            tripPlanner.addOneData(node1);
            tripPlanner.removeOneData(node1);
            assertEquals(0, tripPlanner.getTotalPlaces());
        }

    /**
     * Tests if the findShortestTrips returns a correct path of nodes from advanced graph to ensure the FD does not face any issues when displaying them.
     */
    @Test
        void testFindShortestTrips() {
            try {
                tripPlanner.loadData("good file");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // puts the nodes into the hastable and tests if the value pair of node is passed into the AE method parameter correctly.
            tripPlanner.getNodes().put("good node1", new NodeInterfaceBD("good node1", "node1"));
            tripPlanner.getNodes().put("good node2", new NodeInterfaceBD("good node2", "node2"));

            List<String> expected = new LinkedList<>(Arrays.asList("node1", "node2", "node3"));
            List<String> expected2 = new LinkedList<>(Arrays.asList("node4", "node5", "node6"));

            // Gets the list of nodes when goodnode 1 and 2 is passed.
            List<List<NodeInterfaceBD>> actual = tripPlanner.findShortestTrips("good node1,good node2");

            // 0: [node1, node2, node3] , 1: [node1,node2,node3]
            for(int i =0; i< actual.size();i++){
                if(i ==0){
                    assertEquals(expected, actual.get(i));
                }else{
                    assertEquals(expected2, actual.get(i));
                }
            }
        }

    /**
     * Tests if the reference to the graph was correctly set to null.
     */
    @Test
    void testRemoveTree() {
            try {
                tripPlanner.loadData("good file");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            tripPlanner.removeTree();
            assertThrows(NullPointerException.class, ()-> tripPlanner.getTotalPlaces());
        }

    /**
     * Tests if the code handles the return value of AE's shortestpath correctly.
     */
    @Test
        void testFindShortestPath() {
            try {
                tripPlanner.loadData("good file");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            List<String> expected = new LinkedList<>(Arrays.asList("node1", "node2", "node3"));
            // Should return the hardcodes list of nodes if nodes are correct.
            List<NodeInterfaceBD> actual = tripPlanner.findShortestPath(new NodeInterfaceBD("good node1", "node one"), new NodeInterfaceBD("good node2", "node two"));
            assertEquals(expected, actual);
        }

    /**
     * Tests if the code handles the value returns by AE's shortestpathcost correctly given good and bad nodes.
     */
    @Test
    void testShortestPathCost() {
            try {
                tripPlanner.loadData("good file");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            double expected = 3.0;
            double actual = tripPlanner.shortestPathCost(new NodeInterfaceBD("good node", "node one"), new NodeInterfaceBD("good node", "node two"));
            assertEquals(expected, actual);
            expected = 0.0;
            // bad node, there shld be no path cost
            actual = tripPlanner.shortestPathCost(new NodeInterfaceBD("bad node", "node one"), new NodeInterfaceBD("bad node", "node two"));
            assertEquals(expected, actual);
        }
}
