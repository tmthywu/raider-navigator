import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.sound.sampled.*;

public class Menu extends JPanel {
    // the mainDisplay.
    private MainDisplay mainDisplay;

    // the background.
    private Sprite menuBackground = new Sprite(0, 0, "images/menuBackground.png");

    // sounds
    private Sound fileSavedSound = new Sound("sounds/file-saved.wav");

    // file io
    private PrintWriter output;

    // gui variables
    private JTextField inputStart;
    private JTextField inputDest;
    private JButton submit;
    private JButton toggleFloor;
    private JButton clearScreen;
    private JButton getTextFile;

    public Menu(MainDisplay mainDisplay) {
        // setting up the JPanel
        this.mainDisplay = mainDisplay;

        // initializing JTextFields, setting bounds, and adding to the panel
        inputStart = new JTextField("Enter start room");
        inputDest = new JTextField("Enter dest room");
        inputStart.setBounds(500, 50, 120, 50);
        inputDest.setBounds(500, 100, 120, 50);
        add(inputStart);
        add(inputDest);

        // initializing JButtons, setting bounds, adding action listeners, and adding to the panel
        submit = new JButton("Get route");
        toggleFloor = new JButton("Toggle floor");
        clearScreen = new JButton("Clear screen");
        getTextFile = new JButton("Get text file");
        add(submit);
        add(toggleFloor);
        add(clearScreen);
        add(getTextFile);
        submit.setBounds(650, 30, 100, 20);
        toggleFloor.setBounds(650, 70, 100, 20);
        clearScreen.setBounds(650, 110, 100, 20);
        getTextFile.setBounds(650, 150, 100, 20);
        submit.addActionListener(new SubmitButtonListener());
        toggleFloor.addActionListener(new ToggleFloorButtonListener());
        clearScreen.addActionListener(new ClearScreenButtonListener());
        getTextFile.addActionListener(new GetTextFileButtonListener());

        // adding a line listener to the fileSaved sound effect
        fileSavedSound.addLineListener(new FileSavedSoundListener());
    }
    
    // paintcomponent
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // drawing the background
        menuBackground.draw(g);
    }

    // button listeners
    public class SubmitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // getting input from textfields
            String startText = inputStart.getText();
            String destText = inputDest.getText();
            startText = startText.replace(" ", "").toLowerCase();
            destText = destText.replace(" ", "").toLowerCase();

            // throwing an exception if the input is not valid
            if (!mainDisplay.getGraph().getKeySet().contains(mainDisplay.getGraph().getNode(startText)) || !mainDisplay.getGraph().getKeySet().contains(mainDisplay.getGraph().getNode(destText))) {
                throw new IllegalArgumentException("Please enter the name of a room / room number in the school (e.g. 1069, cafeteria)");
            } 
            else if (startText.equals(destText)) {
                throw new IllegalArgumentException("Please enter two different rooms in the school.");
            }
            else {
                // putting text from text fields into mainDisplay's start and dest nodenames
                mainDisplay.setStartingNode(startText);
                mainDisplay.setDestNode(destText);

                // letting the mainDisplay draw the route!
                mainDisplay.setPrintRoute(true);
            }
        }
    }

    public class ToggleFloorButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // switching the floor mainDisplay currently displays
            if (mainDisplay.getFloor() == 1) {
                mainDisplay.setFloor(2);
            } else {
                mainDisplay.setFloor(1);
            }
        }
    }

    public class ClearScreenButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // resetting everything 
            mainDisplay.setEta(0);
            mainDisplay.setPrintRoute(false);
            mainDisplay.clearRoute();
            mainDisplay.getFilledCircles().clear();
            mainDisplay.getOpenCircles().clear();
            System.out.println(mainDisplay.getRoute().size());
        }
    }

    public class GetTextFileButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // throwing an exception if there isn't a route
            if (mainDisplay.getRoute().size() == 0) {
                throw new IllegalArgumentException("Please get a route first!");
            }
            else {
                // initializing the file
                String fileName = "lineByLines/" + mainDisplay.getStart() + "to" + mainDisplay.getDest() + ".txt";
                File newFile = new File(fileName);
                try {
                    output = new PrintWriter(newFile);
                } catch (FileNotFoundException e) {
                }
                // printing the directions
                mainDisplay.printDirections(output);
                // playing sound effect
                fileSavedSound.start();
                // closing output stream
                output.close();
            }
        }
    }

    // line listeners
    public class FileSavedSoundListener implements LineListener {
        public void update(LineEvent event) {
            // resetting the frame position of carSound once the sound is done
            if (event.getType() == LineEvent.Type.STOP) {
                fileSavedSound.flush();
                fileSavedSound.setFramePosition(0);
            }
        }
    }
}
