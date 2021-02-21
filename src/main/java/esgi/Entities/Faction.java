package esgi.Entities;

import java.util.ArrayList;
import esgi.Display.*;

public class Faction {
    private String name;
    private int satisfaction;
    private int partisans;
    private final Output out;

    public Faction(String name, int satisfaction, int partisans) {
        this.name = name;
        this.satisfaction = satisfaction;
        this.partisans = partisans;
        this.out = new Output();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(int satisfaction) {
        this.satisfaction = satisfaction;
    }

    public int getPartisans() {
        return partisans;
    }

    public void setPartisans(int partisans) {
        this.partisans = partisans;
    }

    public int totalAppreciation(){
        return satisfaction * partisans;
    }

    public void deduceSatisfaction(int amount) {
        satisfaction = Math.max(0, satisfaction - (satisfaction/amount));
        out.displaySatisfaction(name, satisfaction);
    }


    public boolean bribe(Resources resource) {
        int totalCash = 15 * partisans;

        if (totalCash > resource.getTreasury()) {
            out.bribeFailed(partisans, totalCash);
            return false;

        } else {
            addSatisfaction(10);
            out.bribeSucceeded(partisans, totalCash);
            out.displaySatisfaction(name, satisfaction);

            return true;
        }

    }

    public void growth(int islandPop){
        float newPartisans = (float)partisans + islandPop / 100 * (int)Math.random() * 10 + 1;
        satisfaction = Math.min(100, (int)newPartisans);
    }

    public void kill(int percentage){
        float newPartisans = (float)partisans - (float)partisans / 100 * percentage;
        partisans = Math.max(0, (int)newPartisans);
    }

    public void addSatisfaction(int percentage){
        float newSatisfaction = (float)satisfaction + (((float)satisfaction / 100) * percentage);
        satisfaction = Math.min(100, (int)newSatisfaction);
    }
}
