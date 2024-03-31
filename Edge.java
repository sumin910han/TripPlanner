public class Edge extends Number implements EdgeInterface {
	private NodeInterface start;
	private NodeInterface destination;
	private Number weight;
	private boolean isOpen;
	private String color;

	public Edge(NodeInterface start, NodeInterface destination, int weight) {
		this.start = start;
		this.destination = destination;
		this.weight = weight;
		this.isOpen = true;
		this.color = "#000000"; // default color
	}

	@Override
	public NodeInterface getStart() {
		return start;
	}

	@Override
	public NodeInterface getDestination() {
		return destination;
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
		this.isOpen = isOpen;
	}

	@Override
	public boolean getStatus() {
		return isOpen;
	}

	@Override
	public void setColor(String hex) {
		this.color = hex;
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return start.getName() + " -> " + destination.getName() + " (" + weight + ")";
	}

	@Override
	public boolean equals(Object o) {
		// check that the given object is not null
		if (o != null) {
			// check that the object is an Edge
			if (o instanceof Edge) {
				boolean sameStart, sameEnd, sameWeight, sameColor;
				// compare start node names and descriptions
				if (start == null) {
					sameStart = (((Edge) o).getStart() == null);
				} else {
					boolean sameStartName, sameStartDesc;
					if (start.getName() == null) {
						sameStartName = ((Edge) o).getStart().getName() == null;
					} else {
						sameStartName = start.getName().equals(((Edge) o).getStart().getName());
					}
					if (start.getDescription() == null) {
						sameStartDesc = ((Edge) o).getStart().getDescription() == null;
					} else {
						sameStartDesc = start.getDescription().equals(((Edge) o).getStart().getDescription());
					}
					sameStart = sameStartName && sameStartDesc;
				}
				// compare destination node names and descriptions
				if (destination == null) {
					sameEnd = (((Edge) o).getDestination() == null);
				} else {
					boolean sameEndName, sameEndDesc;
					if (destination.getName() == null) {
						sameEndName = ((Edge) o).getDestination().getName() == null;
					} else {
						sameEndName = destination.getName().equals(((Edge) o).getDestination().getName());
					}
					if (destination.getDescription() == null) {
						sameEndDesc = ((Edge) o).getDestination().getDescription() == null;
					} else {
						sameEndDesc = destination.getDescription().equals(((Edge) o).getDestination().getDescription());
					}
					sameEnd = sameEndName && sameEndDesc;
				}
				// compare edge weights
				if (weight == null) {
					sameWeight = (((Edge) o).getWeight() == null);
				} else {
					sameWeight = weight.equals(((Edge) o).getWeight());
				}
				// compare edge colors
				if (color == null) {
					sameColor = ((Edge) o).getColor() == null;
				} else {
					sameColor = color.equals(((Edge) o).getColor());
				}
				// return true if all values are the same
				if (sameStart && sameEnd && sameWeight && sameColor && isOpen == ((Edge) o).getStatus()) {
					return true;
				}
			}
		}
		// otherwise return false
		return false;
	}

	@Override
	public int intValue() {
		return weight.intValue();
	}

	@Override
	public long longValue() {
		return weight.longValue();
	}

	@Override
	public float floatValue() {
		return weight.floatValue();
	}
}
