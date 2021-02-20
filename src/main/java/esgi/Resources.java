package esgi;

public class Resources {
    private final Output out = new Output();
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

    public void addTreasury(int sum) {
        treasury += sum;
    }

    public void deduceTreasury(int sum) {
        treasury += sum * -1;
    }

    public void addFood(int sum) {
        food += sum;
    }

    public void deduceFood(int sum) {
        food += sum * -1;
    }

    public boolean canBuy(int sum) {
        return treasury >= sum;
    }

    public boolean canBuild(int industry, int agriculture) {
        return industry + agriculture <= 100;
    }

    public void addIndustry(int value) {
        int newIndustry = industry + value;
        if(canBuild(newIndustry , agriculture)) {
            setIndustry(newIndustry);
        }
        else {
            out.industryFull();
        }
    }

    public void addAgriculture(int value) {
        int newAgriculture = agriculture + value;
        if(canBuild(newAgriculture ,industry)) {
            setIndustry(newAgriculture);
        }
        else {
            out.agricultureFull();
        }
    }
}
