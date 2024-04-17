public class Node {
    private String nodeName;
    private int x;
    private int y;
    private int floor;

    // constructors
    Node() {

    }

    Node(String newName, int x, int y, int floor) {
        this.nodeName = newName;
        this.x = x;
        this.y = y;
        this.floor = floor;
    }

    // getters and setters
    public String getName() {
        return nodeName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getFloor() {
        return floor;
    }
    //------------------------------------------------------------------------------------------

    public double getDistance(Node otherNode) {
        return Math.hypot(x - otherNode.getX(), y - otherNode.getY());
    }

    public String toString() {
        return nodeName;
    }

    public boolean equals(String otherNode) {
        if (nodeName.equals(otherNode)) {
            return true;
        }
        return false;
    }
}
