import java.util.*;

public class Pair {
    private ArrayList<Node> path;
    private Double distance;

    // constructor
    Pair(ArrayList<Node> path, Double distance) {
        this.path = path;
        this.distance = distance;
    }

    // getters
    public ArrayList<Node> getPath() {
        return path;
    }

    public Double getDist() {
        return distance;
    }
}
