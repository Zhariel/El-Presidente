package esgi;

import java.io.IOException;

import esgi.Managers.GameManager;

public class Launcher {
    /**
     * Used exclusively to launch the game loop
     * Catches IOException
     */

    public static void main(String[] args) {

        GameManager game = GameManager.getInstance();

        try {
            game.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
