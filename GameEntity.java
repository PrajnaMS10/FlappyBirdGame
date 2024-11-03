package flappybird.game.entity;

import flappybird.interfaces.Drawable;
import flappybird.interfaces.Moveable;
import java.awt.Graphics;
import java.awt.Image;

public abstract class GameEntity implements Drawable, Moveable {
    protected int x, y, width, height;
    protected Image img;

    public GameEntity(int x, int y, int width, int height, Image img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    // Getters and setters
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}