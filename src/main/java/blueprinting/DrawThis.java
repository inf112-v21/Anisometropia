package blueprinting;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class DrawThis {
    private ShapeRenderer shapeRenderer;
    public DrawThis() {
        shapeRenderer = new ShapeRenderer();
    }

    public void drawRect(float x, float y, float width, float height) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0f, 0f, 0f, 1);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }

    public void drawCircle(float x, float y, float radius, int segments) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0f, 0f, 0f, 1);
        shapeRenderer.circle(x, y, radius, segments);
        shapeRenderer.end();
    }

    public void drawTriangle(float x1, float y1, float z1, float x2, float y2, float z2) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0f, 0f, 0f, 1);
        shapeRenderer.triangle(x1, y1, z1, x2, y2, z2);
        shapeRenderer.end();
    }


}

