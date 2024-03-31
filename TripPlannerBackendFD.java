import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;

public class TripPlannerBackendFD implements TripPlannerBackendBDInterface{

	ArrayList<EdgeInterface> edges = new ArrayList<>();
	ArrayList<NodeInterface> nodes = new ArrayList<>();

	@Override
	public void loadData(String filename) throws FileNotFoundException {
		edges.clear();
		nodes.clear();
		Node a = new Node("Algorithm Dijkstra", "1");
		Node b = new Node("B", "2");
		Node c = new Node("C", "3");
		Node d = new Node("D", "4");
		Node e = new Node("E", "5");
		Node f = new Node("F", "6");
		
		Edge aa = new Edge(a, a, 0);
		a.addEdgeLeaving(aa);
		a.addEdgeEntering(aa);
		Edge bb = new Edge(b, b, 0);
		b.addEdgeLeaving(bb);
		b.addEdgeEntering(bb);
		Edge cc = new Edge(c, c, 0);
		c.addEdgeLeaving(cc);
		c.addEdgeEntering(cc);
		Edge dd = new Edge(d, d, 0);
		d.addEdgeLeaving(dd);
		d.addEdgeEntering(dd);
		
		Edge ee = new Edge(e, e, 0);
		e.addEdgeLeaving(ee);
		e.addEdgeEntering(ee);
		Edge ff = new Edge(f, f, 0);
		f.addEdgeLeaving(ff);
		f.addEdgeEntering(ff);

		Edge ab = new Edge(a, b, 2);
		a.addEdgeLeaving(ab);
		b.addEdgeEntering(ab);
		Edge bc = new Edge(b, c, 3);
		b.addEdgeLeaving(bc);
		c.addEdgeEntering(bc);
		Edge cd = new Edge(c, d, 19);
		c.addEdgeLeaving(cd);
		d.addEdgeEntering(cd);
		Edge da = new Edge(d, a, 6);
		d.addEdgeLeaving(da);
		a.addEdgeEntering(da);
		
		Edge ba = new Edge(b, a, 100);
		
		b.addEdgeLeaving(ba);
		a.addEdgeEntering(ba);
		edges.add(ab);
		edges.add(bc);
		edges.add(cd);
		edges.add(da);
		
		edges.add(ba);
		
		edges.add(aa);
		edges.add(bb);
		edges.add(cc);
		edges.add(dd);
		edges.add(ee);
		edges.add(ff);

		nodes.add(a);
		nodes.add(b);
		nodes.add(c);
		nodes.add(d);
		nodes.add(e);
		nodes.add(f);
	}

//	@Override
//	public void removeOneData(NodeInterface dataToRemove) {
//		ArrayList<NodeInterface> t = new ArrayList<>();
//		for (var v : nodes) {
//			if (v.equals(dataToRemove))
//				continue;
//			t.add(v);
//		}
//		nodes.clear();
//		nodes.addAll(t);
//		ArrayList<Edge> tmp = new ArrayList<>();
//		for (var v : edges) {
//			if (v.getStart().equals(dataToRemove) || v.getDestination().equals(dataToRemove))
//				continue;
//			tmp.add(v);
//		}
//		edges.clear();
//		edges.addAll(tmp);
//
//	}

	@Override
	public void addOneData(NodeInterface nodeToAdd) {
		nodes.add(nodeToAdd);
		edges.add(new Edge(nodeToAdd, nodeToAdd, 0));
	}

	@Override
	public List<List<NodeInterface>> findShortestTrips(String input) throws NoSuchElementException {
		ArrayList<List<NodeInterface>> al = new ArrayList<>();
		ArrayList<NodeInterface> a = new ArrayList<>();
		a.add(nodes.get(0));
		ArrayList<NodeInterface> b = new ArrayList<>();
		b.add(nodes.get(0));
		b.add(nodes.get(1));
		ArrayList<NodeInterface> c = new ArrayList<>();
		c.add(nodes.get(0));
		c.add(nodes.get(1));
		c.add(nodes.get(2));
		ArrayList<NodeInterface> d = new ArrayList<>();
		d.add(nodes.get(0));
		d.add(nodes.get(1));
		d.add(nodes.get(2));
		d.add(nodes.get(3));
		al.add(a);
		al.add(b);
		al.add(c);
		al.add(d);
		return al;
	}

	@Override
	public void removeTree() {
		nodes.clear();
		edges.clear();
	}

//	@Override
//	public List<NodeInterface> findShortestPath(NodeInterface start, NodeInterface end) {
//		ArrayList<NodeInterface> d = new ArrayList<>();
//		d.add(nodes.get(0));
//		d.add(nodes.get(1));
//		d.add(nodes.get(2));
//		d.add(nodes.get(3));
//		return d;
//	}

//	@Override
//	public double shortestPathCost(NodeInterface startNode, NodeInterface endNode) {
//		return 5.3;
//	}

	@Override
	public int getTotalPlaces() {
		return nodes.size();
	}

	@Override
	public List<EdgeInterface> getMST() {
		return edges;
	}

	public Hashtable<String, NodeInterface> getNodes(){
		Hashtable<String, NodeInterface> ht = new Hashtable<>();
		for(var v : nodes){
			ht.put(v.getName(), v);
		}
		return ht;
	}

	@Override
	public void toggleEdge(String[] input) {
		// TODO Auto-generated method stub

	}

//	@Override
//	public Hashtable<String, NodeInterface> getNodes() {
//		Hashtable<String, NodeInterface>ht = new Hashtable<>();
//		for(var v : nodes) {
//			ht.put(v.getName(), v);
//		}
//		return ht;
//	}

	@Override
	public List<NodeInterface> findShortestPath(String startNodeName, String endNodeName) {
		ArrayList<NodeInterface> d = new ArrayList<>();
		d.add(nodes.get(0));
		d.add(nodes.get(1));
		d.add(nodes.get(2));
		d.add(nodes.get(3));
		return d;
	}

	@Override
	public double shortestPathCost(String startNodeName, String endNodeName) {
		return 5.3;
	}

}
