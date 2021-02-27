package blueprinting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class Blueprint {
    DrawThis drawThis;
    WriteThis writeThis;

    public Blueprint() {
        int y = Gdx.graphics.getWidth();
        int x = Gdx.graphics.getHeight();

        drawThis = new DrawThis();
        writeThis = new WriteThis();
    }

    public void render() {
        // MENU ITEMS
        for (int i = 0; i < 5; i++) {
            drawThis.drawRect(32+(i*106),912f,102f,32f);
            writeThis.write(Color.BLACK, "Menu Button "+(i+1), 36f+(i*106), 688f);
        }

        // TITLE OF THE GAME - "ROBO RALLY!"
        drawThis.drawRect(640,912f,448f,32f);
        writeThis.write(Color.BLACK, "GAME TITLE: ROBO RALLY!", 770, 933f);

        // MULTIPLAYER VIEW
        for (int i = 0; i < 7; i++) {
            drawThis.drawRect(762f,598f-(i*46),166f,42f);
            writeThis.write(Color.BLACK, "Player "+(i+1), 784f, 626f-(i*46));
        }

        // CARDS TO CHOOSE FROM (choice of 9)
        for (int i = 0; i < 9; i++) {
            drawThis.drawRect(32f+(i*84),208f,84f,96f);
            writeThis.write(Color.BLACK, "Option\nCard "+(i+1), 44f+(i*84), 286f);
        }

        // DAMAGE INDICATOR (10 in total)
        for (int i = 0; i < 10; i++) {
            drawThis.drawTriangle(32f+(i*61),196f,96f+(i*61),192f, 64f+(i*61),148f);
            writeThis.write(Color.BLACK, "!", 62f+(i*61), 182f);
        }

        // INFO SCREEN
        drawThis.drawRect(672f,32f,256f,160f);
        writeThis.write(Color.BLACK, "Info box", 770f, 120f);

        // POWER DOWN
        drawThis.drawCircle(864f, 256f, 46f, 32);
        writeThis.write(Color.BLACK, "Power down", 824f, 260f);

        // CARDS (choice of 5)
        for (int i = 0; i < 5; i++) {
            drawThis.drawRect(32f+(i*108),32f,84f,96f);
            writeThis.write(Color.BLACK, "Chosen\nCard "+(i+1), 48f+(i*108), 102f);
        }

        // TIMER
        writeThis.write(Color.BLACK, "Timer", 598f, 88f);
        drawThis.drawTriangle(580f,128f,652f,128f, 616f,82f);
        drawThis.drawTriangle(580f,32f,652f,32f, 616f,82f);
    }
}

