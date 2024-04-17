import javax.swing.*;

public class App extends JFrame {
    Menu menu;
    MainDisplay mainDisplay;

    private App() throws Exception {
        // setting up the JFrame
        this.setLayout(null); // allows me to set custom bounds
        this.setTitle("Raider Navigator");
        this.setSize(Const.WIDTH, Const.HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // adding in JPanels, setting layouts, and setting bounds
        this.mainDisplay =  new MainDisplay();
        this.menu = new Menu(mainDisplay);
        add(menu);
        menu.setLayout(null);
        menu.setBounds(0, 0, Const.WIDTH, 200);
        add(mainDisplay);
        mainDisplay.setBounds(0, 200, Const.WIDTH, Const.HEIGHT - 200);
        setVisible(true);
    }

    public void runGameLoop() {
        while (true) {
            // 1. Clear the game window and redraw everything
            repaint();
            // 2. pause the program for 10 mS, so human eye can perceive the drawing; pause
            // the program for 10 mS
            try {
                Thread.sleep(Const.FRAME_PERIOD);
            } catch (Exception e) {
            }
            // 3. Repeat
        }
    }

    public static void main(String[] args) throws Exception {
        new App().runGameLoop();
    }
}