package flappybird.game.entity;

import java.awt.Image;

public class Pipe extends GameEntity {
    private boolean passed;
    private final int velocityX;

    public Pipe(int x, int y, int width, int height, Image img) {
        super(x, y, width, height, img);
        this.passed = false;
        this.velocityX = -6;
    }

    @Override
    public void move() {
        x += velocityX;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}