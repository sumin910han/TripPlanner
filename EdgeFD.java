
public class EdgeFD implements EdgeInterface{
	
	Number weight;
	NodeInterface start;
	NodeInterface end;
	boolean status = true;
	String color = "0000FF";
	
	public EdgeFD(NodeInterface start, NodeInterface end, Number weight) {
		this.start = start;
		this.end = end;
		this.weight = weight;
		start.addEdgeLeaving(this);
		end.addEdgeEntering(this);
	}

	@Override
	public NodeInterface getStart() {
		return start;
	}

	@Override
	public NodeInterface getDestination() {
		return end;
	}

	@Override
	public Number getWeight() {
		return weight;
	}

	@Override
	public double doubleValue() {
		return weight.doubleValue();
	}

	@Override
	public void setStatus(boolean isOpen) {
		status = isOpen;
		
	}

	@Override
	public boolean getStatus() {
		return status;
	}

	@Override
	public void setColor(String hex) {
		this.color = hex;
	}

	@Override
	public String getColor() {
		return color;
	}

}
