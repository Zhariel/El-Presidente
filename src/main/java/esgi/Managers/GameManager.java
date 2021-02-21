package esgi.Managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import esgi.Display.Output;
import esgi.Init.GameInitializer;
import esgi.Entities.*;
import esgi.Menu.*;

/**
 * GameManager is a singleton class
 * As long as there is a GameManager object, the game is going
 * Where the game is over, we refresh the object and start the loop
 */

public class GameManager {

    private static GameManager instance = null;
    private final Output out = new Output();
    private static int seasonCount;
    private static String season;
    public static HashMap<String, String> seasonMap = new HashMap<String, String>();

    private GameManager() {
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
            seasonCount = 0;
            seasonMap.put("automne", "\uD83C\uDF42");
            seasonMap.put("hiver", "\u2744");
            seasonMap.put("printemps", "\uD83C\uDF41");
            seasonMap.put("été", "\u2600");
            seasonMap.put("misc", "");
        }

        return instance;
    }

    public int getSeasonCount() {
        return seasonCount;
    }

    public void setSeasonCount(int seasonCount) {
        GameManager.seasonCount = seasonCount;
    }

    /**
     * updates the season string based on season count
     */
    public void updateSeason() {
        season = seasonCount % 4 == 0 ? "automne" :
                (seasonCount % 4 == 1 ? "hiver" : (seasonCount % 4 == 2 ? "printemps" : "été"));
    }

    /**
     * produces resources
     * @param resources
     */
    public void produceResources(Resources resources) {
        if(!season.equals("automne") || seasonCount == 0) return;
            resources.produceFood();
            resources.produceMoney();
    }

    /**
     * how many partisans there are on the island
     * @param factions
     * @return
     */
    public int partisanSum(ArrayList<Faction> factions) {
        return factions.stream()
                .mapToInt(Faction::getPartisans)
                .sum();
    }

    /**
     * total appreciation over the entire island
     * @param factions
     * @return
     */
    public int islandAppreciation(ArrayList<Faction> factions) {
        if (partisanSum(factions) == 0 ) return 0;

        return factions.stream()
                .mapToInt(Faction::totalAppreciation)
                .sum()
                / partisanSum(factions);
    }

    /**
     * If it's autumn and not the 1st year,
     * we check the amount of food and kill or reproduce partisans accordingly.
     * @param factions
     * @param totalFood
     * @param resources
     */
    public void managePartisans(ArrayList<Faction> factions, int totalFood, Resources resources) {
        if (seasonCount == 0 || seasonCount % 4 != 0) return;
        int foodFromAgriculture = (int) resources.getAgriculture() * 40;

        int sum = partisanSum(factions);

        if (totalFood < sum * 4) {
            eliminatePartisans(factions, totalFood);
            return;
        } else if (foodFromAgriculture > sum * 4) {
            reproducePartisans(factions);
        }
        resources.substractFood(sum * 4);
    }

    /**
     * kills a small amount of partisans until there is enough food for everyone
     * @param factions
     * @param totalFood
     */
    public void eliminatePartisans(ArrayList<Faction> factions, int totalFood) {
        Random rand = new Random();
        int sum;
        do {
            sum = partisanSum(factions);
            int idx = rand.nextInt(8);
            factions.get(idx).kill(10);

        } while (totalFood < sum);

        isGameOver(factions);
    }

    /**
     * Iterates over the factions and randomly reproduces partisans
     * @param factions
     */
    public void reproducePartisans(ArrayList<Faction> factions) {
        factions.forEach(f -> f.growth(partisanSum(factions)));
    }

    /**
     * checks if the game is over by summing the overall approval
     * @param factions
     */
    public void isGameOver(ArrayList<Faction> factions){
        int islandApproval = islandAppreciation(factions);
        int treshold = 30;

        if (islandApproval < treshold) {
            gameOver(islandApproval, treshold);
        }
    }

    /**
     * throws a game over and gives you the opportunity to restart the game
     * @param islandApproval
     * @param treshold
     */
    public void gameOver(int islandApproval, int treshold) {
        out.gameOver(islandApproval, treshold);
        Scanner sc = new Scanner(System.in);
        sc.next();

        instance = null;
        GameManager game = GameManager.getInstance();
        try {
            game.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Builds all the game objects
     * Loops infinitely until you either close the program or
     * lose, in which case a new GameManager is generated
     * @throws IOException
     */
    public void run() throws IOException {
        System.out.println();
        updateSeason();

        GameInitializer initializer = new GameInitializer("sandbox");
        EventManager events = new EventManager();
        Scanner scan = new Scanner(System.in);
        MenuAction menu = new MenuAction();

        Resources resource = initializer.initResources();
        ArrayList<Faction> factions = initializer.initFactions();
        ArrayList<Event> eventList = events.populateEventList(seasonMap);

        //eventList.forEach(out::debugEvent);


        //Game loop
        while (true) {
            produceResources(resource);
            managePartisans(factions, resource.getFood(), resource);
            out.describeGameState(resource, factions, season, seasonMap, seasonCount);
            out.approbation(islandAppreciation(factions));

            int chosenEvent = events.diceRoll(season);
            if (events.applyChoice(chosenEvent, season, eventList, resource, factions)) {
                out.describeGameState(resource, factions, season, seasonMap, seasonCount);
            }

            menu.endTurn(resource, factions);

            managePartisans(factions, resource.getFood(), resource);
            seasonCount++;
            updateSeason();
            isGameOver(factions);
        }
    }
}
