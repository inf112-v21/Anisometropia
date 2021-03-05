package screens;

import com.badlogic.gdx.graphics.Texture;

public class GameButton {

    int x;
    int y;
    int width;
    int height;
    boolean isActive;
    Texture texture;

    public GameButton(int x, int y, int width, int height, boolean isActive, Texture texture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isActive = isActive;
        this.texture = texture;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
