package esgi.Entities;

import java.util.ArrayList;
import java.util.HashMap;

public class Option {
    private String decision;
    private String consequence;
    private HashMap factionImpact;
    private int treasuryImpact;
    private int foodImpact;
    private int industryImpact;
    private int agricultureImpact;

    public Option(String decision, String consequence, int treasuryImpact, int foodImpact, int industryImpact, int agricultureImpact) {
        this.decision = decision;
        this.consequence = consequence;
        this.factionImpact = new HashMap();
        this.foodImpact = foodImpact;
        this.treasuryImpact = treasuryImpact;
        this.industryImpact = industryImpact;
        this.agricultureImpact = agricultureImpact;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getConsequence() {
        return consequence;
    }

    public void setConsequence(String consequence) {
        this.consequence = consequence;
    }

    public HashMap getFactionImpact() {
        return factionImpact;
    }

    public void setFactionImpact(HashMap factionImpact) {
        this.factionImpact = factionImpact;
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

    public int getIndustryImpact() {
        return industryImpact;
    }

    public void setIndustryImpact(int industryImpact) {
        this.industryImpact = industryImpact;
    }

    public int getAgricultureImpact() {
        return agricultureImpact;
    }

    public void setAgricultureImpact(int agricultureImpact) {
        this.agricultureImpact = agricultureImpact;
    }
}
