import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;

import edu.wisc.cs.cs400.JavaFXTester;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.util.Pair;

public class TripPlannerFrontendTests extends JavaFXTester{
	
	@BeforeEach
	public void print() {
		//System.out.println("Running");
	}
	
	public TripPlannerFrontendTests() {
		super(TripPlannerFrontendFD.class);
	//	System.out.println("Console");
	}
	/**
	 * test Delete Map button
	 */
	@Test
	public void test1() {
		System.out.println("Test1");
		TripPlannerFrontendFD instance = TripPlannerFrontendFD.last_created_instance;
		
		assertEquals(instance.nodegraph.size(), 0);
		assertEquals(instance.edgeendings.size(), 0);
		assertEquals(instance.edgegraph.size(), 0);
		assertEquals(instance.labelgraph.size(), 0);
		assertEquals(instance.edgelabels.size(), 0);
		
		instance.state = TripPlannerFrontendFD.State.visualize;
		clickOn("#DeleteMap");
		assertEquals(instance.state, TripPlannerFrontendFD.State.init);
		
		assertEquals(instance.nodegraph.size(), 0);
		assertEquals(instance.edgeendings.size(), 0);
		assertEquals(instance.edgegraph.size(), 0);
		assertEquals(instance.labelgraph.size(), 0);
		assertEquals(instance.edgelabels.size(), 0);
	}
	
	/**
	 * Test Load map
	 * 
	 */
	@Test
	public void test2() {
		
		TripPlannerFrontendFD instance = TripPlannerFrontendFD.last_created_instance;
		//Replace normal class, if present, with a new backend placeholder
		if(!(instance.backend instanceof TripPlannerBackendFD)){
			instance.backend = new TripPlannerBackendFD();
			System.out.println("Set backend!");
		}
		//Relies on having the tester
		Platform.runLater(()->instance.loadMap("Placeholder"));
		boolean finished = false;
		for(int i = 0; i < 250; i++) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(instance.state == TripPlannerFrontendFD.State.loaded) {
				finished = true;
				break;
			}
			
		}
		if(!finished) {
			throw new RuntimeException("Bad state, exit");
		}
		
		//Ensure all the nodes have been created
		assertEquals(instance.nodegraph.size(), 6);
		//Ensure all the labels have been created
		assertEquals(instance.labelgraph.size(), 6);
		
		assertEquals(instance.edgegraph.size(), 5);
		assertEquals(instance.edgelabels.size(), 5);
		assertEquals(instance.edgeendings.size(), 5);
		
		System.out.println(instance.state);
		
	}
	
	/**
	 * Test transform for node rendering
	 */
	@Test
	public void test3() {
		TripPlannerFrontendFD instance = TripPlannerFrontendFD.last_created_instance;
		Pair<TripPlannerFrontendFD.Point, TripPlannerFrontendFD.Point> p = instance.getTransform(0, 0, 100, 100);
		//Following will error if it's null
		double x1 = p.getKey().x;
		double y1 = p.getKey().y;
		double x2 = p.getValue().x;
		double y2 = p.getValue().y;
		assertEquals(x1, 46, 0.5);
		assertEquals(y1, 25, 0.5);
		assertEquals(x2, 75, 0.5);
		assertEquals(y2, 54, 0.5);
		
	}
	
	/**
	 * test display information
	 */
	@Test
	public void test4() {
		TripPlannerFrontendFD instance = TripPlannerFrontendFD.last_created_instance;
		NodeInterface n = new NodeFD("Name of a node", "This is the description of a node");
		Platform.runLater(()->instance.displayInfo(n));
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Label l = lookup("#label_info").query();
		String str = l.getText();
		assertTrue(str.contains("Name of a node"));
		assertTrue(str.contains("This is the description of a node"));
	}
	
	/**
	 * Test initial state
	 */
	@Test
	public void test5() {
		System.out.println("Test");
		Label l = lookup("#promptlabel").query();
		Label l2 = lookup("#label_info").query();
		assertEquals("Load a map to see its visualization here", l.getText());
		assertEquals("Select a node to show its information here", l2.getText());
		//Get the instance so we can inspect the fields
		TripPlannerFrontendFD instance = TripPlannerFrontendFD.last_created_instance;
		
		assertEquals(instance.nodegraph.size(), 0);
		assertEquals(instance.edgeendings.size(), 0);
		assertEquals(instance.edgegraph.size(), 0);
		assertEquals(instance.labelgraph.size(), 0);
		assertEquals(instance.edgelabels.size(), 0);
		
	}
}
