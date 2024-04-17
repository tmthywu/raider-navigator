import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class MainDisplay extends JPanel {
    // variables
    private int eta;
    private int floor = 1;
    private boolean printRoute = false;

    // nodes
    private String start;
    private String dest;

    // route
    private ArrayList<Node> route = new ArrayList<Node>();

    // gui variables
    private JLabel etaLabel;
    private JLabel floorLabel;

    // files
    private File coordinates = new File("coordinates.txt");

    // pictures
    private Sprite floor1 = new Sprite(0, 0, "images/floor1.png");
    private Sprite floor2 = new Sprite(0, 0, "images/floor2.png");
    private ArrayList<Sprite> filledCircles = new ArrayList<Sprite>();
    private ArrayList<Sprite> openCircles = new ArrayList<Sprite>();

    // graph of the school (each room is a node)
    private Graph myGraph;

    public MainDisplay() throws Exception {
        // setting up the JPanel

        // initializing JLabels and setting bounds
        etaLabel = new JLabel("ETA: " + eta + " seconds | ");
        etaLabel.setFont(Const.BOLD_FONT);
        etaLabel.setForeground(Color.WHITE);
        add(etaLabel);
        etaLabel.setBounds(350, 0, 50, 20);

        floorLabel = new JLabel("Floor " + floor);
        floorLabel.setFont(Const.BOLD_FONT);
        floorLabel.setForeground(Color.WHITE);
        add(floorLabel);

        // reading in graph from file
        ArrayList<Node> nodes = new ArrayList<Node>();
        // each line is a node + its coordinates + its connections
        ArrayList<String[]> lines = new ArrayList<String[]>(); 
        
        Scanner input = new Scanner(coordinates);

        // for each line in the file
        while (input.hasNextLine()) {
            String line = input.nextLine();
            // skipping the line if it's an empty line
            if (line.equals("")) {
                continue;
            }
            // splitting the line into its components
            String[] lineParts = line.split(" ");

            // storing the split line to be accessed later when adding edges 
            lines.add(lineParts);
            // adding a new node to the arraylist of nodes based on the line's input
            nodes.add(new Node(lineParts[0], Integer.parseInt(lineParts[1]), Integer.parseInt(lineParts[2]), Integer.parseInt(lineParts[3])));
        }

        input.close();

        // creating the graph using the arraylist of nodes
        myGraph = new Graph(nodes);

        // adding edges for the graph
        for (int i = 0; i < nodes.size(); i++) {
            Node currentNode = nodes.get(i);
            for (int j = 4; j < lines.get(i).length; j++) {
                Node destNode = myGraph.getNode(lines.get(i)[j]);
                myGraph.addEdge(currentNode, destNode);
            }
        }

        // // bidirectional test; checks if all edges are bidirectional
        // for (Node n : myGraph.getKeySet()) {
            
        //     for (Edge e : myGraph.getAdjList().get(n)) {
        //         // loops through the list of edges for the specified node

        //         Node otherNode = e.getDest();
        //         if (!myGraph.hasEdge(otherNode, otherNode.getName(), n.getName())) {
        //             System.out.println("BIDIRECTIONAL TEST FAILED: " + n + " " + otherNode);
        //         } else {
        //             System.out.println("passed");
        //         }
        //     }
        // }
    }

    // getters and setters
    public int getEta() {
        return eta;
    }

    public int getFloor() {
        return floor;
    }

    public ArrayList<Node> getRoute() {
        return route;
    }

    public Graph getGraph() {
        return myGraph;
    }

    public ArrayList<Sprite> getFilledCircles() {
        return filledCircles;
    }

    public ArrayList<Sprite> getOpenCircles() {
        return openCircles;
    }

    public String getStart() {
        return start;
    }

    public String getDest() {
        return dest;
    }

    public void setFloor(int newFloor) {
        floor = newFloor;
        floorLabel.setText("Floor " + floor);
    }

    public void setEta(int newEta) {
        eta = newEta;
    }

    public void setPrintRoute(boolean newPrintRoute) {
        printRoute = newPrintRoute;
    }

    public void setStartingNode(String newNode) {
        start = newNode;
    }

    public void setDestNode(String newNode) {
        dest = newNode;
    }

    public void clearRoute() {
        route.clear();
    }

    // drawing the panel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // drawing the map
        if (floor == 1)
            floor1.draw(g);
        else 
            floor2.draw(g);

        // // testing (shows all nodes)
        // for (Node n: myGraph.getKeySet()) {
        //     if (this.floor == n.getFloor()) {
        //         g.drawRect(n.getX() - 4, n.getY() - 4, 8, 8);
        //     }
        // }

        // setting text for the JLabel
        etaLabel.setText("ETA: " + eta + " seconds | ");

        // drawing the route if printroute
        if (printRoute) {
            drawRoute(g2, myGraph, myGraph.getNode(start), myGraph.getNode(dest));
        }

        // drawing start/end points + open circles
        for (Sprite filledCircle : filledCircles) {
            filledCircle.draw(g);
        }
        for (Sprite openCircle : openCircles) {
            openCircle.draw(g);
        }
    }

    public void drawRoute(Graphics2D g2, Graph myGraph, Node start, Node dest) {
        // setting the colour and thickness of the lines to be drawn
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(3));

        // clearing arraylists to "restart" the route drawing process
        filledCircles.clear();
        openCircles.clear();

        // running dijkstra's algorithm and getting both the path and the eta
        Pair result = myGraph.findShortestPath(start, dest);
        route = result.getPath();
        // route.clear();
        // eta calculation: distance (px) * (10m/50px) / (1.42 m/s) = eta (s)
        double distance = result.getDist();
        eta = (int) Math.round(distance * (10 / 50.0) / (1.42));

        // set all coordinates of Sprite ArrayLists; add and delete objects as needed
        for (int i = 0; i < route.size(); i++) {
            // if starting node and is on the same floor maindisplay is currently displaying
            if (i == 0 && this.floor == route.get(i).getFloor()) {
                // drawing a filled circle there
                filledCircles.add(new Sprite(route.get(i).getX() - 4, route.get(i).getY() - 4, "images/filledCircle.png"));
            } 
            // if dest node and is on the same floor maindisplay is currently displaying
            else if (i == route.size() - 1 && this.floor == route.get(i).getFloor())  {
                // drawing a filled circle there
                filledCircles.add(new Sprite(route.get(i).getX() - 4, route.get(i).getY() - 4, "images/filledCircle.png"));
                break;
            } 
            else if (route.get(i).toString().contains("stairs") && !route.get(i).toString().contains("toph") && !route.get(i).toString().contains("stairsh") && !route.get(i).toString().contains("2h") && !route.get(i).toString().contains("3h") && !route.get(i).toString().contains("4h") && !route.get(i).toString().contains("5h") && !route.get(i).toString().contains("6h")) {
                // if the node is a stair and is on the same floor maindisplay is currently displaying
                openCircles.add(new Sprite(route.get(i).getX() - 4, route.get(i).getY() - 4, "images/openCircle.png"));
            }

            // drawing route between nodes that are on the same floor maindisplay is currently displaying
            if (this.floor == route.get(i).getFloor()) {
                g2.drawLine(route.get(i).getX(), route.get(i).getY(), route.get(i+1).getX(), route.get(i+1).getY());
            }
        }
    }

    public void printDirections(PrintWriter output) {
        // path is in reverse order, so we need to reverse it
        route = reverseArrayList(route);

        Node currentNode = route.get(0);
        Node nextNode = route.get(1);
        int deltaX = nextNode.getX() - currentNode.getX();
        int deltaY = currentNode.getY() - nextNode.getY();

        int orientation; // 0 = N; 1 = E; 2 = S; 3 = W

        // establishing initial orientation with some buffer room for error
        if (Math.abs(deltaX) < 5 && deltaY > 0) {
            orientation = 0;
        } else if (Math.abs(deltaX) < 5) {
            orientation = 2;
        } else if (deltaX > 0) {
            orientation = 1;
        } else {
            orientation = 3;
        }

        // loopoing through each node in the path
        for (int i = 0; i < route.size() - 1; i++) {
            currentNode = route.get(i);
            nextNode = route.get(i+1);

            // accounting for stairs
            if (currentNode.getName().contains("stairs") && !currentNode.getName().contains("top") && nextNode.getName().contains("stairs") && nextNode.getName().contains("top")) {
                output.println("Go up the stairs.");
                continue;
            }

            // calculating change in x and change in y
            deltaX = nextNode.getX() - currentNode.getX();
            // need to flip order for deltaY because java's coordinate system is: as you move down, y increases, which is the opposite of cartesian
            deltaY = currentNode.getY() - nextNode.getY();

            // using the quadrant to adjust the angle from currentNode to nextNode
            int quadrant = 1;
            double angle;
            // accounting for quadrantal angles first
            if (deltaX == 0 && deltaY > 0) {
                angle = Math.PI/2;
            } else if (deltaX == 0 & deltaY < 0) {
                angle = 3 * Math.PI/2;
            } else if (deltaY == 0 && deltaX > 0) {
                angle = 0;
            } else if (deltaY == 0 && deltaX < 0) {
                angle = Math.PI;
            } else {
                // using arctan + quadrant to calculate the angle
                angle = Math.atan(deltaY / deltaX); 

                // getting the quadrant
                if (deltaX > 0 && deltaY > 0) {
                    quadrant = 1;
                }
                else if (deltaX < 0 && deltaY > 0) {
                    quadrant = 2;
                }
                else if (deltaX < 0 && deltaY < 0) {
                    quadrant = 3;
                }
                else if (deltaX > 0 && deltaY < 0) {
                    quadrant = 4;
                }

                // adjusting the angle based on the quadrant
                switch (quadrant) {
                    case 1: 
                        break;
                    case 2: 
                        angle = Math.PI + angle;
                        break;
                    case 3: 
                        angle = Math.PI + angle;
                        break;
                    case 4: 
                        angle = 2 * Math.PI + angle;
                        break;
                    default: System.out.println("quadrant -> angle ERROR");
                }
            }

            // adjusting the angle to the current orientation
            switch (orientation) {
                case 0: 
                    break;
                case 1: 
                    angle = (angle + Math.PI/2) % (2 * Math.PI);
                    break;
                case 2: 
                    angle = (angle + Math.PI) % (2 * Math.PI);
                    break;
                case 3: 
                    angle = (angle + 3*Math.PI/2) % (2 * Math.PI);
                    break;
                default: System.out.println("get orientationAngle ERROR");
            }

            // calculating the nature of movement from currentNode to nextNode
            // account for quadrant 4 first because otherwise the math doesn't work out
            if (3 * Math.PI/2 < angle && angle <= 2 * Math.PI) {
                output.println("Turn right towards " + nextNode + ".");
                if (orientation == 3) {
                    orientation = 0;
                } else {
                    orientation++;
                }
            }
            else if (Math.abs(angle - Math.PI /2) <= Math.PI/6) { 
                output.println("Go straight towards " + nextNode + ".");
            } 
            else if (angle - Math.PI/2 > Math.PI/6 && angle - Math.PI/2 <= Math.PI/3) {
                output.println("Veer left towards" + nextNode + ".");
                // no change in orientation
            }
            else if (angle - Math.PI/2 < -Math.PI/6 && angle - Math.PI/2 >= -Math.PI/3) {
                output.println("Veer right towards" + nextNode + ".");
                // no change in orientation
            }
            else if (angle - Math.PI/2 > Math.PI/3) {
                output.println("Turn left towards " + nextNode + ".");
                if (orientation == 0) {
                    orientation = 3;
                } else {
                    orientation--;
                }
            }
            else if (angle - Math.PI/2 < -Math.PI/3) {
                output.println("Turn right towards " + nextNode + ".");
                if (orientation == 3) {
                    orientation = 0;
                } else {
                    orientation++;
                }
            }
            else {
                System.out.println("calculation ERROR");
            }

            // printing a special message once the user arrives at the destination
            if (i == route.size() - 2) {
                output.println("You have arrived at your destination.");
            }
        }
    }

    public static ArrayList<Node> reverseArrayList(ArrayList<Node> arr) {
        ArrayList<Node> newArr = new ArrayList<Node>(arr.size());
        // looping through the original arraylist backwards and appending each element to the new arraylist
        for (int i = arr.size() - 1; i >= 0; i--) {
            newArr.add(arr.get(i));
        }
        return newArr;
    }
}
