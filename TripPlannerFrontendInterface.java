import javafx.stage.Stage;

public interface TripPlannerFrontendInterface {
   //public TripPlannerFrontendXX(TripPlannerBackend backend);
   public void start(final Stage stage) throws Exception;
   public void initialize(); // Initializes the TripPlannerFrontend to a blank state. This is used for resetting the application at the start of the program and when deleting the map
   public void planTrip(); // Calls the backend functions to plan the trip, then redraws the graph
   public void addPlace(); // Pops up a dialog that asks for information about the place to add, then calls the appropriate backend function
   public void loadData(String filename); // Pops up a file dialog to prompt the user to find a file. Cleanly handles operations such as choosing an invalid file, choosing a directory instead of a file (will recursively load everything in it), and choosing a file with incorrect (e.g. 330 instead of normal 644) permissions. Calls backend load data and refreshes screen. Intended as a callback for the Load Map button
   public void drawGraph(); // Redraws the graph section of the screen
   public void displayInfo(NodeInterface n); // Displays information about the node in the bottom right corner of the screen
   public void optimizeNodePlacements(); //Helper function, should attempt to move nodes around such that they don’t overlap with edges, if possible.
   public void toggleDarkMode(); // Toggles dark mode on and off. In dark mode, white and black are swapped for the interface elements, and nodes and edges are drawn with lighter colours to provide better contrast.
   //Public <List> 
}

//--module-path /Users/hansumin/desktop/CS400/javafx-sdk-20/lib --add-modules javafx.controls,javafx.fxml
