package launcher;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import screens.GameScreenManager;

public class GameApplication extends Game {

    public SpriteBatch spriteBatch;
    public GameScreenManager gameScreenManager;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        gameScreenManager = new GameScreenManager(this);
//        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    @Override
    public void dispose() {
        super.dispose();
        spriteBatch.dispose();
        gameScreenManager.dispose();
    }

}