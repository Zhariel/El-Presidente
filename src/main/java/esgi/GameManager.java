package esgi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GameManager {

    private static GameManager instance = null;
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

    public void run() throws IOException {
        System.out.println();
        updateSeason();

        GameInitializer initializer = new GameInitializer("sandbox");
        EventManager events = new EventManager();
        Output out = new Output();
        Scanner scan = new Scanner(System.in);
        MenuAction menu = new MenuAction();

        Resources resource = initializer.initResources();
        ArrayList<Faction> factions = initializer.initFactions();
        ArrayList<Event> eventList = events.populateEventList(seasonMap);

        //eventList.forEach(out::debugEvent);


        //Game loop
        while (true) {
            out.describeGameState(resource, factions, season, seasonMap, seasonCount);

            int chosenEvent = events.diceRoll(season);
            if(events.applyChoice(chosenEvent, season, eventList, resource, factions)) {
                out.describeGameState(resource, factions, season, seasonMap, seasonCount);
            }



            menu.endTurn(resource, factions);

            seasonCount++;
            updateSeason();
        }
    }
}
