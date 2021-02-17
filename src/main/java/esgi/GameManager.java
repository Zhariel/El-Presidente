package esgi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GameManager {

    private static GameManager instance = null;
    private static int seasonCount;
    private static String season;
    public static HashMap seasonSymbol = new HashMap();

    private GameManager(){};

    public static GameManager getInstance(){
        if(instance == null){
            instance = new GameManager();
            seasonCount = 0;
            seasonSymbol.put("automne", "\uD83C\uDF42");
            seasonSymbol.put("hiver", "\u2744");
            seasonSymbol.put("printemps", "\uD83C\uDF41");
            seasonSymbol.put("été", "\u2600");
        }

        return instance;
    }

    public int getSeasonCount() {
        return seasonCount;
    }

    public void setSeasonCount(int seasonCount) {
        this.seasonCount = seasonCount;
    }

    public void updateSeason(){
        season = seasonCount%4 == 0 ? "automne" :
                (seasonCount%4 == 1 ? "hiver": (seasonCount%4 == 2 ? "printemps" : "été"));
    }

    public void describeGameState(Resources resource, ArrayList<Faction> factions){

        for(Faction f : factions){
            System.out.print(f.getName());
            System.out.print(" : [satisfaction : " + f.getSatisfaction() + "] ");
            System.out.print(", [partisans : " + f.getPartisans() + "] ");
            System.out.println();
        }
        System.out.println();

        System.out.println("C'est l'an " + (seasonCount/4+1) + ", saison " + (seasonCount%4+1) + " de votre règne.");
        System.out.println("Saison : " + season + " " + seasonSymbol.get(season));
        System.out.println();

    }

    public void run() throws IOException{
        System.out.println();
        updateSeason();

        GameInitializer initializer = new GameInitializer("sandbox");
        EventManager events = new EventManager();

        Resources r = initializer.initResources();
        ArrayList<Faction> factions = initializer.initFactions();

        //Game loop
        while(true){
            describeGameState(r, factions);
            System.out.println("Qu'allez-vous faire ?");

            events.diceRoll(season);





//            Scanner s = new Scanner(System.in);
//            String test = s.nextLine();
//            System.out.println(test);

            seasonCount++;
            updateSeason();
            break;
        }
    }
}
