import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

/**
 * load up a GUI to show the map show the shortest path between two places.
 * 
 * @author hansumin
 *
 */
public class TripPlannerFrontendFD extends Application implements TripPlannerFrontendInterface {

	static TripPlannerFrontendFD last_created_instance = null;

	/**
	 * hold a point
	 * 
	 * @author hansumin
	 *
	 */
	class Point {
		double x;
		double y;
	}

	// JavaFx Stage
	Stage stage;
	// JavaFx Scene
	Scene scene;
	// JavaFx Pane
	Pane pane;

	// buttons
	Button button_LoadMap;
	Button button_DeleteMap;
	Button button_TripPlan;
	Button button_Visualize;

	// labels
	Label information;
	Label load_prompt;

	// UI elements for nodes rendering
	ArrayList<Pair<Circle, NodeInterface>> nodegraph = new ArrayList<>();
	ArrayList<Pair<Label, NodeInterface>> labelgraph = new ArrayList<>();
	ArrayList<Pair<Line, EdgeInterface>> edgegraph = new ArrayList<>();
	ArrayList<Pair<Circle, EdgeInterface>> edgeendings = new ArrayList<>();
	ArrayList<Pair<Label, EdgeInterface>> edgelabels = new ArrayList<>();

	// start and end nodes in the shortest patg
	NodeInterface startNode;
	NodeInterface endNode;

	String startNodeName;
	String endNodeName;
	// Backend
	TripPlannerBackendBDInterface backend;

	/**
	 * use the state machine to control the display
	 * 
	 * @author hansumin
	 *
	 */
	enum State {
		init, loaded, planned, visualize
	}

	// current State, default is init
	State state;

	/**
	 * does first time setup of the frontend
	 */
	@Override
	public void start(final Stage stage) {
		last_created_instance = this; // Hacky stuff for tester
		System.out.println("launching");

		// TODO
		// TODO
		// TODO
		// During integration, replace this with the real backend class.
		backend = new TripPlannerBackendBD(new GraphReader(),new AdvancedGraphAE());

		pane = new Pane();
		scene = new Scene(pane, 1280, 720);
		this.stage = stage;
		stage.setTitle("TripPlanner");

		initialize();

		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
	}

	/**
	 * System entry point. JavaFX does not cooperate well with other stuff, such as
	 * running from a different class, so this class will double as the main class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Run a single instance of the Frontend. It will handle
		// setup later.
		Application.launch();
	}

	/**
	 * start over from the initialized state
	 */
	@Override
	public void initialize() {
		// Initialize stuff
		// Remove all components from the Pane, then create and add new ones
		pane.getChildren().clear();
		// Remove all drawn nodes
		nodegraph.clear();
		labelgraph.clear();
		edgegraph.clear();
		edgeendings.clear();
		edgelabels.clear();
		// Initialize buttons
		button_LoadMap = new Button("Load Map");
		button_LoadMap.setLayoutX(970);
		button_LoadMap.setLayoutY(10);
		button_LoadMap.setMinWidth(300);
		button_LoadMap.setMinHeight(60);
		button_LoadMap.setFont(Font.font(36));
		;
		pane.getChildren().add(button_LoadMap);
		button_LoadMap.setId("LoadMap");

		button_DeleteMap = new Button("Delete Map");
		button_DeleteMap.setLayoutX(970);
		button_DeleteMap.setLayoutY(90);
		button_DeleteMap.setMinWidth(300);
		button_DeleteMap.setMinHeight(60);
		button_DeleteMap.setFont(Font.font(36));
		;
		pane.getChildren().add(button_DeleteMap);
		button_DeleteMap.setId("DeleteMap");

		button_TripPlan = new Button("Trip plan");
		button_TripPlan.setLayoutX(970);
		button_TripPlan.setLayoutY(170);
		button_TripPlan.setMinWidth(300);
		button_TripPlan.setMinHeight(60);
		button_TripPlan.setFont(Font.font(36));
		;
		pane.getChildren().add(button_TripPlan);
		button_TripPlan.setId("TripPlan");

		button_Visualize = new Button("Visualize");
		button_Visualize.setLayoutX(970);
		button_Visualize.setLayoutY(250);
		button_Visualize.setMinWidth(300);
		button_Visualize.setMinHeight(60);
		button_Visualize.setFont(Font.font(36));
		;
		pane.getChildren().add(button_Visualize);
		button_Visualize.setId("Visualize");

//		button_AddPlaces = new Button("Add Places");
//		button_AddPlaces.setLayoutX(970);
//		button_AddPlaces.setLayoutY(330);
//		button_AddPlaces.setMinWidth(300);
//		button_AddPlaces.setMinHeight(60);
//		button_AddPlaces.setFont(Font.font(36));
//		;
//		pane.getChildren().add(button_AddPlaces);
//
//		button_ToggleEdges = new Button("ToggleEdges");
//		button_ToggleEdges.setLayoutX(970);
//		button_ToggleEdges.setLayoutY(410);
//		button_ToggleEdges.setMinWidth(300);
//		button_ToggleEdges.setMinHeight(60);
//		button_ToggleEdges.setFont(Font.font(36));
//		;
//		pane.getChildren().add(button_ToggleEdges);

		information = new Label();
		information.setLayoutX(970);
		information.setLayoutY(340);
		information.setMinWidth(300);
		information.setMinHeight(10);
		information.setMaxWidth(300);
		information.setMaxHeight(210);
		information.setWrapText(true);
		information.setId("label_info");

		load_prompt = new Label();
		load_prompt.setLayoutX(0);
		load_prompt.setLayoutY(0);
		load_prompt.setText("Load a map to see its visualization here");
		load_prompt.setId("promptlabel");
		// pane.getChildren().add(load_prompt);

		resetInformation();

		// information.setBackground(new Background(new
		// BackgroundFill(Paint.valueOf("000000"), CornerRadii.EMPTY, Insets.EMPTY)));
		pane.getChildren().add(information);

		Line divide_vert = new Line(960, 0, 960, 720);
		pane.getChildren().add(divide_vert);

		Line divide_hori = new Line(960, 330, 1280, 330);
		pane.getChildren().add(divide_hori);

		button_DeleteMap.setOnMouseClicked((e) -> initialize());
		button_LoadMap.setOnMouseClicked((e) -> loadMap());
		button_TripPlan.setOnMouseClicked((e) -> planTrip());
		button_Visualize.setOnMouseClicked((e) -> visualize());

		state = State.init;// Has just been initialized

		drawGraph();

	}

	/**
	 * display the shortest path if it exists
	 */
	void visualize() {
		// Check state
		// If state is init, display error popup "You need to load a graph first and
		// plan your trip"
		if (state == State.init) {
			notificationPopUp("You need to load a graph first and plan your trip");
			return;
		}
		// If state is Loaded, display error message "You need to plan your trip first"
		if (state == State.loaded) {
			notificationPopUp("You need to plan your trip first");
			return;
		}
		// If state is visualized, display error message "You're already looking at it"
		if (state == State.visualize) {
			notificationPopUp("You're already looking at it");
			return;
		}
		// If state is planned, proceed
		if (state == State.planned) {
			// Call backend's shortest path algorithm
			try {
				List<NodeInterface> shortestPath = backend.findShortestPath(startNode.getName(), endNode.getName());

				// Catch any error and return if it fails
				if (shortestPath == null) {
					notificationPopUp("Invalid shortest path!");
					return;
				}
			} catch (Exception e) {
				notificationPopUp("Invalid shortest path!");
				e.printStackTrace();
				return;
			}
			// If it succeeded, change the state to visualized
			state = State.visualize;
			// drawGraph()
			drawGraph();
		}
	}

	/**
	 * clear the information label
	 */
	void resetInformation() {
		information.setText("Select a node to show its information here");
	}

	/**
	 * put the start and end place, and color them
	 */
	@Override
	public void planTrip() {
		// [check state]
		// If state is init, pop up an error saying "You need to load a map first"
		if (state == State.init) {
			notificationPopUp("You need to load a map first");
			return;
		}
		// If state is visualize or plan, pop up an error saying "You need to delete the
		// map and reload it first"
		if (state == State.visualize || state == State.planned) {
			notificationPopUp("You need to delete the map and reload it first");
			return;
		}
		// If state is Loaded, proceed
		if (state == State.loaded) {
			// Prompt the user for the starting node. Search through the possible nodes and
			// save it if it's valid
			String startNodeString = promptUser("Where do you want to start?");
			System.out.println("Starting point: " + startNodeString);
			// If it's invalid, display an error and exit.
			NodeInterface start_tmp = null;
			for (var v : nodegraph) {
				System.out.println(v.getValue().getName());
				if (v.getValue().getName().equals(startNodeString)) {
					start_tmp = v.getValue();
				}
			}
			if (start_tmp == null) {
				notificationPopUp("Invalid start point!");
				return;
			}
			// Prompt the user for the ending node. Search through the possible nodes and
			// save it if it's valid
			String endNodeString = promptUser("Where do you want to go?");
			// If it's invalid, display an error and exit.
			NodeInterface end_tmp = null;
			for (var v : nodegraph) {
				if (v.getValue().getName().equals(endNodeString)) {
					end_tmp = v.getValue();
				}
			}
			if (end_tmp == null) {
				notificationPopUp("Invalid end point!");
				return;
			}
			// Set startNode and endNode
			startNode = start_tmp;
			endNode = end_tmp;
			// Set state to planned
			state = State.planned;
			// Redraw the graph
			drawGraph();
		}
	}

	/**
	 * ask the user for the string
	 * 
	 * @param prompt question to display to the user
	 * @return
	 */
	public String promptUser(String prompt) {
		TextInputDialog tid = new TextInputDialog();
		tid.setHeaderText(prompt);
		tid.setTitle("TripPlanner Input");
		tid.showAndWait();

		return tid.getResult();
	}

	@Override
	public void addPlace() {
		// TODO Auto-generated method stub

	}

	/**
	 * load the data from backend
	 */
	@Override
	public void loadData(String filename) {
		try {
			System.out.println("Entry point for backend code");
			System.out.println("loadData, args="+filename);
			backend.loadData(filename);
			{
				//Hook into backend to test
				System.out.println(backend.getNodes().size());

			}
			System.out.println("No caught exceptions, continuing");
		} catch (FileNotFoundException e) {
			notificationPopUp("Something went really, really, wrong");
		}

	}

	/**
	 * draw the map on the leftside of the screen
	 */
	@Override
	public void drawGraph() {
		if (state == State.init) {
			if (!pane.getChildren().contains(load_prompt)) {
				pane.getChildren().add(load_prompt);
			}
			for (var v : nodegraph) {
				if (pane.getChildren().contains(v.getKey())) {
					pane.getChildren().remove(v.getKey());
				}
			}
			for (var v : labelgraph) {
				if (pane.getChildren().contains(v.getKey())) {
					pane.getChildren().remove(v.getKey());
				}
			}
			for (var v : edgegraph) {
				if (pane.getChildren().contains(v.getKey())) {
					pane.getChildren().remove(v.getKey());
				}
			}
			for (var v : edgeendings) {
				if (pane.getChildren().contains(v.getKey())) {
					pane.getChildren().remove(v.getKey());
				}
			}
			for (var v : edgelabels) {
				if (pane.getChildren().contains(v.getKey())) {
					pane.getChildren().remove(v.getKey());
				}
			}
			// clean the map before drawing the new map
			nodegraph.clear();
			labelgraph.clear();
			edgegraph.clear();
			edgelabels.clear();
			edgeendings.clear();
			return;
		}

		if (state == State.loaded) {
//			if (pane.getChildren().contains(load_prompt)) {
//				pane.getChildren().remove(load_prompt);
//			}
			load_prompt.setText("Drag nodes around for better visualization!     Format: (start)-----o(end)");
		}
		/**
		 * This code (commented) parses based on an edge list
		 */
//		List<EdgeInterface> edges = backend.getMST();
//		HashSet<NodeInterface> nodes = new HashSet<>();
//		// get the all edges
//		for (var v : edges) {
//			if (!nodes.contains(v.getStart())) {
//				nodes.add(v.getStart());
//			}
//			if (!nodes.contains(v.getDestination())) {
//				nodes.add(v.getDestination());
//			}
//		}
		/**
		 * This code path parses based off a node list
		 */
		Hashtable<String, NodeInterface> nodes_tmp = backend.getNodes();
		HashSet<NodeInterface> nodes = new HashSet<>();
		ArrayList<EdgeInterface> edges = new ArrayList<>();
		for (var v : nodes_tmp.keySet()) {
			if (!nodes.contains(nodes_tmp.get(v)))
				nodes.add(nodes_tmp.get(v));
			var node = nodes_tmp.get(v);
			for (var v2 : node.getEdgesLeaving()) {
				if (!edges.contains(v2))
					edges.add(v2);
			}
		}

		renderNodes(nodes);
		renderEdges(edges);
	}

	/**
	 * 
	 * @param edges
	 */
	void renderEdges(List<EdgeInterface> edges) {
		if (state == State.loaded) {
			for (var v : edges) {
				NodeInterface starttmp = v.getStart();
				NodeInterface endtmp = v.getDestination();
				Circle c1 = null;
				Circle c2 = null;
				for (var v2 : nodegraph) {
					if (v2.getValue().equals(starttmp)) {
						c1 = v2.getKey();
					}
					if (v2.getValue().equals(endtmp)) {
						c2 = v2.getKey();
					}
				}
				// get the real coordinate
				Pair<Point, Point> transform = getTransform(c1.getCenterX(), c1.getCenterY(), c2.getCenterX(),
						c2.getCenterY());
				if (transform == null)
					continue;
				// draw the line with the real coordinate
				Line l = new Line(transform.getKey().x, transform.getKey().y, transform.getValue().x,
						transform.getValue().y);
				Pair<Line, EdgeInterface> e = new Pair<>(l, v);
				edgegraph.add(e);
				pane.getChildren().add(l);
				// draw circle at the endpoint of the line
				Circle c_end = new Circle(transform.getValue().x, transform.getValue().y, 10);
				edgeendings.add(new Pair<Circle, EdgeInterface>(c_end, v));
				pane.getChildren().add(c_end);
				Label label = new Label("" + v.getWeight().doubleValue());
				// find the midpoint of the line
				label.setLayoutX((transform.getKey().x + transform.getValue().x) / 2);
				label.setLayoutY((transform.getKey().y + transform.getValue().y) / 2);
				edgelabels.add(new Pair<Label, EdgeInterface>(label, v));
				pane.getChildren().add(label);
			}
		}
	}

	/**
	 * 
	 * Transform shortens line segment by 50 pixels to avoid drawing over nodes, and
	 * shifts it 15 pixels to the right to allow bidirectional edges
	 * 
	 * @param x1 - start x coordinate
	 * @param y1 - start y coordinate
	 * @param x2 - end x coordinate
	 * @param y2 - end y coordinate
	 * @return - null if points are the same, otherwise the packed coordinates of
	 *         the transformed segment
	 */
	Pair<Point, Point> getTransform(double x1, double y1, double x2, double y2) {
		double dx = x2 - x1;
		double dy = y2 - y1;
		if (dx == 0 && dy == 0) {
			return null;
		}
		double theta = 0;
//		if (dx == 0) {
//			theta = dy > 0 ? Math.PI / 2 : Math.PI / 2 * 3;
//		} else {
		theta = Math.atan2(dy, dx);
		if (theta < 0) {
			theta += Math.PI * 2;
		}
//		}
		// double len = Math.sqrt(dx * dx + dy * dy);
		Point start = new Point();
		Point end = new Point();
		// Transform shortens line segment by 50 pixels to avoid drawing
		// over nodes, and shifts it 15 pixels to the right to allow
		// bidirectional edges
		start.x = x1 + 50 * Math.cos(theta) + 15 * Math.cos(theta - Math.PI / 2);
		start.y = y1 + 50 * Math.sin(theta) + 15 * Math.sin(theta - Math.PI / 2);
		end.x = x2 - 50 * Math.cos(theta) + 15 * Math.cos(theta - Math.PI / 2);
		end.y = y2 - 50 * Math.sin(theta) + 15 * Math.sin(theta - Math.PI / 2);
		// Return the built pair.
		return new Pair<Point, Point>(start, end);

	}

	/**
	 * edges visualize the state draw the nodes, and recolor them base of the data
	 * 
	 * @param nodes
	 */
	void renderNodes(HashSet<NodeInterface> nodes) {
		final int wrap = 900;
		final int initial_x = 100;
		int x = initial_x;
		int y = 100;
		// place the nodes initial position
		if (state == State.loaded) {
			for (var v : nodes) {
				// draw the nodes
				Circle c = new Circle(x, y, 50);
				c.setFill(Paint.valueOf("DFA0F8"));
				c.setOnMouseClicked((e) -> displayInfo(v));
				Label l = new Label(v.getName());
				// l.setBackground(new Background(new BackgroundFill(Paint.valueOf("FFFFFF"),
				// CornerRadii.EMPTY, Insets.EMPTY)));
				// set the text size on their
				l.setFont(Font.font(16));
				l.setMinWidth(100);
				l.setMaxWidth(100);
				l.setPrefWidth(100);
				l.setWrapText(true);
				l.setLayoutX(x - 50);
				l.setLayoutY(y - 25);
				l.setTextAlignment(TextAlignment.CENTER);
				l.setAlignment(Pos.CENTER);
				l.resize(100, 50);

				// allow nodes to be dragged
				Point p = new Point();
				c.setOnMousePressed((e) -> {
					p.x = c.getCenterX() - e.getX();
					p.y = c.getCenterY() - e.getY();
					System.out.println("Mouse down");
				});
				// make the nodes not to go out of the screen
				c.setOnMouseDragged((e) -> {
					double newX = e.getX() + p.x;
					double newY = e.getY() + p.y;
					if (newX < 50)
						newX = 50;
					if (newY < 50)
						newY = 50;
					if (newX > 910)
						newX = 910;
					if (newY > 670)
						newY = 670;
					c.setCenterX(newX);
					c.setCenterY(newY);
					l.setLayoutX(newX - 50);
					l.setLayoutY(newY - 25);
					NodeInterface noderef = null;

					// locate the nodes base on the circle
					for (var v2 : nodegraph) {
						if (v2.getKey().equals(c)) {
							noderef = v2.getValue();
						}
					}
					if (noderef == null) {
						System.out.println("weird");
						return;
					}

					// move all edges that enter or leave the nodes
					for (var v2 : edgegraph) {
						if (v2.getValue().getStart().equals(noderef)) {
							double x1 = newX;
							double y1 = newY;
							NodeInterface nt = v2.getValue().getDestination();
							Circle other = null;
							// move endpoint that enter or leave the nodes
							// First, find other circle
							for (var v3 : nodegraph) {
								if (v3.getValue().equals(nt)) {
									other = v3.getKey();
								}
							}
							if (other == null) {
								System.out.println("Weird2");
								return;
							}
							// Set up transform
							double x2 = other.getCenterX();
							double y2 = other.getCenterY();
							Pair<Point, Point> p2 = getTransform(x1, y1, x2, y2);
							if (p2 == null)
								return;
							// Apply transform
							v2.getKey().setStartX(p2.getKey().x);
							v2.getKey().setStartY(p2.getKey().y);
							v2.getKey().setEndX(p2.getValue().x);
							v2.getKey().setEndY(p2.getValue().y);

							// Move the ending circle
							for (var v3 : edgeendings) {
								if (v3.getValue().equals(v2.getValue())) {
									v3.getKey().setCenterX(p2.getValue().x);
									v3.getKey().setCenterY(p2.getValue().y);
								}
							}

							// Move the label
							for (var v3 : edgelabels) {
								if (v3.getValue().equals(v2.getValue())) {
									v3.getKey().setLayoutX((p2.getKey().x + p2.getValue().x) / 2);
									v3.getKey().setLayoutY((p2.getKey().y + p2.getValue().y) / 2);
								}
							}
						}
						// If edge is incoming
						if (v2.getValue().getDestination().equals(noderef)) {
							double x2 = newX;
							double y2 = newY;
							NodeInterface nt = v2.getValue().getStart();
							Circle other = null;
							// Locate other circle
							for (var v3 : nodegraph) {
								if (v3.getValue().equals(nt)) {
									other = v3.getKey();
								}
							}
							if (other == null) {
								System.out.println("Weird2");
								return;
							}
							// Set up transform
							double x1 = other.getCenterX();
							double y1 = other.getCenterY();
							Pair<Point, Point> p2 = getTransform(x1, y1, x2, y2);
							if (p2 == null)
								return;
							// Apply transform
							v2.getKey().setStartX(p2.getKey().x);
							v2.getKey().setStartY(p2.getKey().y);
							v2.getKey().setEndX(p2.getValue().x);
							v2.getKey().setEndY(p2.getValue().y);

							// Move ending node identifier
							for (var v3 : edgeendings) {
								if (v3.getValue().equals(v2.getValue())) {
									v3.getKey().setCenterX(p2.getValue().x);
									v3.getKey().setCenterY(p2.getValue().y);
								}
							}

							// Move label
							for (var v3 : edgelabels) {
								if (v3.getValue().equals(v2.getValue())) {
									v3.getKey().setLayoutX((p2.getKey().x + p2.getValue().x) / 2);
									v3.getKey().setLayoutY((p2.getKey().y + p2.getValue().y) / 2);
								}
							}
						}
					}

				});
				// Put as many nodes horizontally as we can, then move to the next row
				x += 200;
				if (x > wrap) {
					x = initial_x;
					y += 200;
					if (y > 700) {
						y = 150;
					}
				}
				// Set mouse handlers for label to pass through
				l.setOnMouseClicked((e) -> {
					c.getOnMouseClicked().handle(e);
					e.consume();
				});
//				l.setOnMouseDragged((e) -> {
//					c.getOnMouseDragged().handle(e);
//					e.consume();
//				});
//				l.setOnMousePressed((e) -> {
//					c.getOnMousePressed().handle(e);
//					e.consume();
//				});
				// Add everything
				nodegraph.add(new Pair<Circle, NodeInterface>(c, v));
				labelgraph.add(new Pair<Label, NodeInterface>(l, v));
				pane.getChildren().add(c);
				pane.getChildren().add(l);
				// System.out.println(l.getHeight());
				// l.setLayoutY(l.getLayoutY()-l.getHeight()/2);

			}
			return;
		}
		// if planning just finished
		if (state == State.planned) {
			for (var v : nodegraph) {
				// Recolor the start node
				if (v.getValue().equals(startNode)) {
					v.getKey().setFill(Paint.valueOf("F1756B"));
				}
				// Recolor the end node
				if (v.getValue().equals(endNode)) {
					v.getKey().setFill(Paint.valueOf("6BA5F1"));
				}
			}
		}
		// If we need to visualize the shortest path
		if (state == State.visualize) {
			List<NodeInterface> shortestPathNodes = backend.findShortestPath(startNode.getName(), endNode.getName());
			for (var v : nodegraph) {
				// Color the nodes along the way
				if (shortestPathNodes.contains(v.getValue())) {
					v.getKey().setFill(Paint.valueOf("8FF385"));
				}
				// Color the start node
				if (v.getValue().equals(startNode)) {
					v.getKey().setFill(Paint.valueOf("F1756B"));
				}
				// Color the end node
				if (v.getValue().equals(endNode)) {
					v.getKey().setFill(Paint.valueOf("6BA5F1"));
				}
			}
			// Color the edges along the way
			for (int i = 0; i < shortestPathNodes.size() - 1; i++) {
				// Color the lines
				for (var v : edgegraph) {
					if (v.getValue().getStart().equals(shortestPathNodes.get(i))
							&& v.getValue().getDestination().equals(shortestPathNodes.get(i + 1))) {
						// Found the correct edge, need to recolor it
						System.out.println("Found");
						v.getKey().setFill(Paint.valueOf("FF0000"));
						v.getKey().setStroke(Paint.valueOf("FF0000"));
					}
				}
				// Color the ending indicators
				for (var v : edgeendings) {
					if (v.getValue().getStart().equals(shortestPathNodes.get(i))
							&& v.getValue().getDestination().equals(shortestPathNodes.get(i + 1))) {
						System.out.println("Found2");
						v.getKey().setFill(Paint.valueOf("FF0000"));
						v.getKey().setStroke(Paint.valueOf("FF0000"));
					}
				}
			}
		}

//		List<NodeInterface> shortestPathNodes = null;
//		
//		if(state == State.visualize) {
//			shortestPathNodes = backend.findShortestPath(startNode, endNode);
//		}
//		if(state == State.planned || state == State.visualize) {
//			if (state == State.visualize) {
//				if(shortestPathNodes.contains(v)) {
//					c.setFill(Paint.valueOf("8FF385"));
//				}
//			}
//			if(v.equals(startNode)) {
//				c.setFill(Paint.valueOf("F1756B"));
//			}if(v.equals(endNode)) {
//				c.setFill(Paint.valueOf("6BA5F1"));
//			}
//		}
	}

	/**
	 * show the information about the name and description of the nodes
	 */
	@Override
	public void displayInfo(NodeInterface n) {
		System.out.println("Information requested: " + n.getName());
		System.out.println(n.getDescription());
		information.setText("Information\n===========\nName: " + n.getName() + "\nDescription: " + n.getDescription());

	}

	/**
	 * unused, remove in favor of manual dragging
	 */
	@Override
	public void optimizeNodePlacements() {
		// TODO Auto-generated method stub

	}

	/*
	 * unused
	 */
	@Override
	public void toggleDarkMode() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Internal load map. Made package vis for tester
	 * @param s
	 */
	void loadMap(String s) {
		loadData(s);
		state = State.loaded;
		drawGraph();
	}

	/**
	 * load the file from disk and set state
	 */
	void loadMap() {
		switch (state) {
		case init:
			FileChooser f = new FileChooser();
			File file = f.showOpenDialog(stage);
			if (file != null) {
				String s = file.getAbsolutePath();
				loadMap(s);
			}
			break;
		default:
			notificationPopUp("Error: There is already a map loaded. Please delete it first.");
			break;
		}
	}

	/**
	 * display notificationPopUp inform of something
	 * 
	 * @param text
	 */
	private void notificationPopUp(String text) {
		Stage popup = new Stage();
		popup.initOwner(stage);
		popup.initModality(Modality.APPLICATION_MODAL);
		Pane p = new Pane();
		Scene s = new Scene(p, 300, 60);
		popup.setResizable(false);
		Label l = new Label(text);
		l.setMinWidth(10);
		l.setMaxWidth(280);
		l.setLayoutX(10);
		l.setLayoutY(10);
		l.setWrapText(true);
		p.getChildren().add(l);
		popup.setScene(s);
		popup.show();
	}
}
