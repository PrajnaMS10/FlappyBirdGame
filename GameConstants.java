package flappybird.util;

public class GameConstants {
    // Game settings
    public static final int GRAVITY = 1;
    public static final int JUMP_FORCE = -10;
    public static final int PIPE_VELOCITY = -6;

    // Bird settings
    public static final int BIRD_WIDTH = 34;
    public static final int BIRD_HEIGHT = 24;

    // Pipe settings
    public static final int PIPE_WIDTH = 64;
    public static final int PIPE_HEIGHT = 512;
    public static final int PIPE_SPACING = 1000; // Milliseconds between pipe spawns

    // UI settings
    public static final int BUTTON_WIDTH = 150;
    public static final int BUTTON_HEIGHT = 50;

    private GameConstants() {
        // Private constructor to prevent instantiation
    }
}