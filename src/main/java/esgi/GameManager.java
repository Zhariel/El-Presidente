package esgi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

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

    public void updateSeason() {
        season = seasonCount % 4 == 0 ? "automne" :
                (seasonCount % 4 == 1 ? "hiver" : (seasonCount % 4 == 2 ? "printemps" : "été"));
    }

    public void produceResources(Resources resources){
        if(season.equals("automne") && seasonCount != 0){
            resources.produceFood();
            resources.produceMoney();
        }
    }

    public int partisanSum(ArrayList<Faction> factions){
        return factions.stream()
                .mapToInt(Faction::getPartisans)
                .sum();
    }

    public int islandAppreciation(ArrayList<Faction> factions) {
        return factions.stream()
            .mapToInt(Faction::totalAppreciation)
            .sum()
            / partisanSum(factions);
    }

    public void managePartisans(ArrayList<Faction> factions, int totalFood, Resources resources){
        if(seasonCount == 0 || seasonCount%4 != 0) return;
        int foodFromAgriculture = (int)resources.getAgriculture() * 40;

        int sum = partisanSum(factions);

        if(totalFood < sum*4){
            eliminatePartisans(factions, totalFood);
            return;
        }
        else if(foodFromAgriculture > sum*4){
            reproducePartisans(factions);
        }
        resources.substractFood(sum*4);
    }

    public void eliminatePartisans(ArrayList<Faction> factions, int totalFood){
        Random rand = new Random();
        int sum;
        do{
            sum = partisanSum(factions);
            int idx = rand.nextInt(9);
            factions.get(idx).kill(10);

        }while(totalFood < sum);
    }

    public void reproducePartisans(ArrayList<Faction> factions){
        factions.forEach(f -> f.growth(partisanSum(factions)));
    }

    public void gameOver(ArrayList<Faction> factions){
        int islandApproval = islandAppreciation(factions);
        int treshold = 30;

        if(islandApproval < treshold){
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
    }

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
            managePartisans(factions, resource.getFood(), resource);
            out.describeGameState(resource, factions, season, seasonMap, seasonCount);
            out.approbation(islandAppreciation(factions));

            int chosenEvent = events.diceRoll(season);
            if(events.applyChoice(chosenEvent, season, eventList, resource, factions)) {
                out.describeGameState(resource, factions, season, seasonMap, seasonCount);
            }

            menu.endTurn(resource, factions);

            produceResources(resource);
            managePartisans(factions, resource.getFood(), resource);
            seasonCount++;
            updateSeason();
            gameOver(factions);
        }
    }
}
