package flappybird.gui;

import javax.swing.*;
import java.awt.*;
import flappybird.game.FlappyBird;  // Add this import statement

public class GameWindow {
    private JFrame frame;

    public GameWindow() {
        // Create the main JFrame
        frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(screenSize);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
        frame.setUndecorated(false); // Keep the title bar and borders

        // Create the FlappyBird instance
        FlappyBird flappyBird = new FlappyBird(screenSize.width, screenSize.height);
        frame.add(flappyBird);

        frame.pack(); // Pack the frame after adding the game panel
        frame.setVisible(true);
    }
}