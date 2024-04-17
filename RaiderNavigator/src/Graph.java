import java.util.*;

public class Graph {
    private HashMap<Node, ArrayList<Edge>> adjList;

    // constructors
    Graph(List<Node> list) {
        // putting a list of nodes in the adjList, with "empty" edges
        adjList = new HashMap<Node, ArrayList<Edge>>();
        for (Node n : list)
            adjList.put(n, new ArrayList<Edge>());
    }

    // getters and setters
    public HashMap<Node, ArrayList<Edge>> getAdjList() {
        return adjList;
    }

    public Node getNode(String nodeName) {
        for (Node n : adjList.keySet()) {
            if (n.getName().equals(nodeName)) {
                return n;
            }
        }
        return new Node();
    }

    public Set<Node> getKeySet() {
        return adjList.keySet();
    }
    //------------------------------------------------------------------------------------------

    public boolean hasEdge(Node n, String startNode, String endNode) {
        // checks if the edge is bidirectional
        for (Edge j : adjList.get(n)) {
            if (j.getStart().equals(startNode) && j.getDest().equals(endNode))
                return true;
        }
        return false;
    }

    public void addEdge(Node node1, Node node2) {
        // adds an edge from node1 to node2
        adjList.get(node1).add(new Edge(node1, node2));
    }

    public Pair findShortestPath(Node start, Node dest) {
        // a set of visited nodes; initially empty
        Set<Node> visited = new HashSet<Node>();
        // the minimum distance from the starting node to each node in the graph
        HashMap<Node, Double> distances = new HashMap<Node, Double>(); // with a length of the number of nodes in the graph
        // stores the previous node on each node's minimum distance path
        HashMap<Node, Node> previous = new HashMap<Node, Node>();

        for (Node n : adjList.keySet()) {
            // filling the distances hashmap with infinity because initially we don't know how far it takes to get from the starting node to each node in the graph
            distances.put(n, Double.POSITIVE_INFINITY);
            // filling the previous hashmap with null values because initially we don't know the previous node on each node's minimum distance path
            previous.put(n, null);
        }
        // begin the algorithm by assigning a value of 0 to the starting node
        distances.replace(start, 0.0);

        // the algorithm ends when the set of visited nodes equals the adjlist keySet
        while (!visited.equals(adjList.keySet())) {
            // find the minimum distance
            double minDistance = Double.POSITIVE_INFINITY;
            Node minDistanceNode = new Node();
            for (Node n : distances.keySet()) {
                if ((!visited.contains(n)) && distances.get(n) < minDistance) {
                    minDistance = distances.get(n);
                    minDistanceNode = n;
                }
            }
            // visit the minimum distance node
            visited.add(minDistanceNode);
            // if the minimum distance node is the destination, the algorithm is finished.
            if (minDistanceNode == dest) { // just check the memory addresses cuz minDistanceNode is directly assigned to the memory address of nodes, not a copy, when it's initialized
                break;
            }
            // exploring the edges branching off from the minimum distance node and updating the distances hashmap so that on the next iteration, we can determine the minimum distance node
            for (Edge e : adjList.get(minDistanceNode)) {
                if (minDistance + e.getWeight() < distances.get(e.getDest())) {
                    distances.replace(e.getDest(), minDistance + e.getWeight());
                    previous.replace(e.getDest(), e.getStart());
                }
            }
        }

        // getting the path from the start node to the dest node
        ArrayList<Node> path = new ArrayList<Node>(1);
        // start at the dest node and go through the previous hashmap, adding nodes to the path along the way, until we arrive at the start node
        Node currentNode = dest;
        while (currentNode != start) {
            path.add(currentNode);
            currentNode = previous.get(currentNode);
        }
        path.add(start);

        return new Pair(path, distances.get(dest));
    }

    public void printAdjList() {
        // source: https://www.studytonight.com/java-examples/graphs-in-java
        for (Map.Entry mapElement : adjList.entrySet()) {
            Node n = (Node) mapElement.getKey();
            System.out.print(n.getName() + " " + n.getX() + " " + n.getY() + "->");
            ArrayList<Edge> list = adjList.get(n);
            for (Edge a : list)
                System.out.print(a.getDest().getName() + " ");
            System.out.println();
        }
    }
}