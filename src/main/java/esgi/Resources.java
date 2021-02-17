package esgi;

public class Resources {
    private int treasury;
    private int food;
    private int industry;
    private int agriculture;

    public Resources(int treasury, int food, int industry, int agriculture) {
        this.treasury = treasury;
        this.food = food;
        this.industry = industry;
        this.agriculture = agriculture;
    }

    public int getTreasury() {
        return treasury;
    }

    public void setTreasury(int treasury) {
        this.treasury = treasury;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getIndustry() {
        return industry;
    }

    public void setIndustry(int industry) {
        this.industry = industry;
    }

    public int getAgriculture() {
        return agriculture;
    }

    public void setAgriculture(int agriculture) {
        this.agriculture = agriculture;
    }
}
