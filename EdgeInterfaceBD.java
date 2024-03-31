/**
 * Placeholder class that returns the minimum functionalities of EdgeInterface.
 */
public class EdgeInterfaceBD extends Number implements EdgeInterface {
    NodeInterface start;
    NodeInterface destination;

    boolean isOpen;

    int weight;
    String color;
    public EdgeInterfaceBD(NodeInterface start, NodeInterface destination, int weight){
        this.start = start;
        this.destination = destination;
        this.weight = weight;
        isOpen = true;
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

    /**
     * Returns the value of the specified number as an {@code int}.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code int}.
     */
    @Override
    public int intValue() {
        return 0;
    }

    /**
     * Returns the value of the specified number as a {@code long}.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code long}.
     */
    @Override
    public long longValue() {
        return 0;
    }

    /**
     * Returns the value of the specified number as a {@code float}.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code float}.
     */
    @Override
    public float floatValue() {
        return 0;
    }

    @Override
    public double doubleValue() {
        return weight;
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
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String getColor() {
        return color;
    }
}
