package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import launcher.GameApplication;
import org.lwjgl.opengl.GL20;

public abstract class AbstractScreen implements Screen {

    protected final launcher.GameApplication GameApplication;

    protected AbstractScreen(GameApplication gameApplication) {
        this.GameApplication = gameApplication;
    }

    public abstract void update(float delta);

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0.7f, 0.6f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {

    }

}