package blueprinting;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WriteThis {
    private SpriteBatch batch;
    private BitmapFont font;

    public WriteThis() {
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    public void write(Color color, String text, float x, float y) {
        batch.begin();
        font.setColor(color);
        font.draw(batch, text, x, y);
        batch.end();
    }

}
