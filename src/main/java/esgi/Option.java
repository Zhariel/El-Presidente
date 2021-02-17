package esgi;

import java.util.ArrayList;
import java.util.HashMap;

public class Option {
    private String action;
    private String consequence;
    public HashMap factionImpact;
    private int foodImpact;
    private int treasuryImpact;

    public Option(String action, String consequence, int foodImpact, int treasuryImpact) {
        this.action = action;
        this.consequence = consequence;
        this.factionImpact = new HashMap();
        this.foodImpact = foodImpact;
        this.treasuryImpact = treasuryImpact;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getConsequence() {
        return consequence;
    }

    public void setConsequence(String consequence) {
        this.consequence = consequence;
    }

    public int getFoodImpact() {
        return foodImpact;
    }

    public void setFoodImpact(int foodImpact) {
        this.foodImpact = foodImpact;
    }

    public int getTreasuryImpact() {
        return treasuryImpact;
    }

    public void setTreasuryImpact(int treasuryImpact) {
        this.treasuryImpact = treasuryImpact;
    }
}
