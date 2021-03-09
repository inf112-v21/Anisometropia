package screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameButton {

    int x;
    int y;
    int width;
    int height;
    boolean isActive;
    TextureRegion textureRegion;

    public GameButton(int x, int y, int width, int height, boolean isActive, TextureRegion textureRegion) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isActive = isActive;
        this.textureRegion = textureRegion;
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

    public TextureRegion getTexture() {
        return textureRegion;
    }

    public void setTexture(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }
}
