package esgi;

public class Faction {
    private String name;
    private int satisfaction;
    private int partisans;

    public Faction(String name, int satisfaction, int partisans) {
        this.name = name;
        this.satisfaction = satisfaction;
        this.partisans = partisans;
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
}
