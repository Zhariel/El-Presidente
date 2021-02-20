package esgi;

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

    public void zeroMultiplier(){

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

    public void addSatisfaction(int amount){
        int modifier = satisfaction == 0 ? 0 : 1;
        int newSatisfaction = satisfaction + (satisfaction / amount);
        satisfaction = Math.min(100, newSatisfaction) * modifier;
    }
}
