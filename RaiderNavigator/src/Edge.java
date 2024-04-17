public class Edge {
    private Node start;
    private Node destination;
    private double weight;

    Edge(Node start, Node destination) {
        this.start = start;
        this.destination = destination;
        this.weight = start.getDistance(destination);
    }

    public Node getStart() {
        return start;
    }

    public Node getDest() {
        return destination;
    }

    public double getWeight() {
        return weight;
    }

    public String toString() {
        return start + "-" + destination;
    }
}
