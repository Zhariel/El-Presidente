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
    public boolean limitedField(int industry, int agriculture) {
    	if(industry + agriculture > 100)
    		return false;
    	return true;
    }
    
    public void addIndustry(int value) {
    	int newIndustry = getIndustry() + value;
    	if(limitedField(newIndustry , getAgriculture())) {
    		setIndustry(newIndustry);
    	}
    	else {
    		System.out.println("Vous avez atteint la limite de place disponible pour créer des usines industrielles. ");  		
    	}
    }
   
    public void addAgriculture(int value) {
    	int newAgriculture = getAgriculture() + value;
    	if(limitedField(newAgriculture ,getIndustry())) {
    		setIndustry(newAgriculture);
    	}
    	else {
    		System.out.println("Vous avez atteint la limite de place disponible pour créer des champs agricoles. ");  
    	}
    }
    
    
}
