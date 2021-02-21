package esgi;

public class Resources {
    private final Output out = new Output();
    private int treasury;
    private int food;
    private float industry;
    private float agriculture;

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

    public float getIndustry() {
        return industry;
    }

    public void setIndustry(int industry) {
        this.industry = industry;
    }

    public float getAgriculture() {
        return agriculture;
    }

    public void setAgriculture(float agriculture) {
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

    public boolean canBuy(int sum) {
        return treasury >= sum;
    }

    public boolean canBuild(float industry, float agriculture) {
        return industry + agriculture <= 100;
    }

    public void substractFood(int amount){
        food -= amount;
    }

    public void addIndustry(int percentage) {
        float newIndustry = industry + ((industry / 100) * percentage);
        if(canBuild(newIndustry , agriculture)) {
            industry = Math.min(100, newIndustry);
        }
        else {
            out.industryFull();
        }
    }

    public void addAgriculture(int percentage) {
        float newAgriculture = agriculture + ((agriculture / 100) * percentage);
        if(canBuild(industry, agriculture)) {
            agriculture = Math.min(100, (int)newAgriculture);
        }
        else {
            out.agricultureFull();
        }
    }

    public void produceMoney(){
        treasury += industry * 10;
    }

    public void produceFood(){
        food += agriculture * 40;
    }
}
