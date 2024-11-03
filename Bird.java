package flappybird.game.entity;

import java.awt.Image;

public class Bird extends GameEntity {
    private int velocityY;
    private final int gravity;

    public Bird(int x, int y, int width, int height, Image img) {
        super(x, y, width, height, img);
        this.velocityY = 0;
        this.gravity = 1;
    }

    @Override
    public void move() {
        y += velocityY;
        velocityY += gravity;
    }

    public void jump() {
        velocityY = -10;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }
}