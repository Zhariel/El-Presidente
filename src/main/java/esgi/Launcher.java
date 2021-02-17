package esgi;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args){

        GameManager game = GameManager.getInstance();

        try {
            game.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
