package esgi;

import java.util.ArrayList;
import java.util.HashMap;

public class Output {


    public void describeGameState(Resources resource, ArrayList<Faction> factions, String season, HashMap seasonSymbol, int seasonCount) {

        System.out.println("- - - - - - - - - - -");
        System.out.println();
        for (Faction f : factions) {
            System.out.print(f.getName());
            System.out.print(" : [satisfaction : " + f.getSatisfaction() + "] ");
            System.out.print(", [partisans : " + f.getPartisans() + "] ");
            System.out.println();
        }
        System.out.println();

        System.out.println("C'est l'an " + (seasonCount / 4 + 1) + ", saison " + (seasonCount % 4 + 1) + " de votre règne.");
        System.out.println("Saison : " + season + " " + seasonSymbol.get(season));
        System.out.println();

        displayRessources(resource);
        System.out.println();
        System.out.println("- - - - - - - - - - -");
        System.out.println();

    }

    public void bribeSucceeded(int number, int amount) {
        System.out.println("Vous avez soudoyé " + number + " partisans avec " + amount + "€.");
        System.out.println();
    }

    public void bribeFailed(int number, int amount) {
        System.out.println("Argent insuffisant pour soudoyer " + number + " partisans avec " + amount + "€.");
        System.out.println();
    }

    public void displaySatisfaction(String faction, int satisfaction) {
        System.out.println("Satisfaction des " + faction + " : " + satisfaction + "%.");
        System.out.println();
    }

    public void suggestChoices() {
        System.out.println("Faites votre choix : \n\n[1] Faire un pot de vin\n[2] Acheter de la nourriture sur le marché\n[3] Finir le tour");
        System.out.println();
    }

    public void boughtFood(Resources resource, int amount, int totalCost) {
        System.out.println("Vous avez acheté " + amount + " unités pour " + totalCost + "€.");
        System.out.println();
        displayRessources(resource);
        System.out.println();
    }

    public void cannotBuyFood() {
        System.out.println("Fonds insuffisants.");
        System.out.println();
    }

    public void promptFoodAmount() {
        System.out.println("Entrez le nombre d'unités à acheter. 1 unité -> 8€");
    }

    public void promptFaction() {
        System.out.println("Entrez la faction :");
    }

    public void promptAmount() {
        System.out.println("Nombre de personnes à soudoyer :");
    }

    public void displayRessources(Resources resource) {
        System.out.println("Trésor : " + resource.getTreasury());
        System.out.println("Nourriture : " + resource.getFood());
        System.out.println("Industrie : " + resource.getIndustry() + "%");
        System.out.println("Agriculture : " + resource.getAgriculture() + "%");
    }

    public void displayInvalid() {
        System.out.println("Entrée invalide.");
        System.out.println();
    }

    public void debugOption(Option o){
        System.out.println(". . . .");
        System.out.println(o.getDecision());
        System.out.println(o.getConsequence());
        System.out.println("trésor : " + o.getTreasuryImpact());
        System.out.println("nourriture : " + o.getFoodImpact());
        System.out.println("industrie : " + o.getIndustryImpact());
        System.out.println("agriculture : " + o.getAgricultureImpact());
        System.out.println(". . . .");
    }

    public void debugEvent(Event e, boolean displayOptions) {
        System.out.println("----");
        System.out.println(e.getSeason());
        System.out.println(e.getNarrative());
        System.out.println(e.getIndex());

        System.out.println();

        Option o = (Option) e.options.get(0);

        if(displayOptions) {
            e.options.forEach(option -> {
                debugOption((Option) option);
            });
        }

        System.out.println(o.getFactionImpact().size());
        o.getFactionImpact().forEach((k, v) -> System.out.println(k + " " + v));
        System.out.println("----");
        System.out.println();
    }

    public void industryFull(){
        System.out.println("Vous avez atteint la limite de place disponible pour créer des usines industrielles. ");
    }

    public void agricultureFull(){
        System.out.println("Vous avez atteint la limite de place disponible pour créer des champs agricoles. ");
    }

    public void displayOptionToChoose(Option o, int index){
        System.out.println(index + " - " + o.getDecision());
    }

    public void displayEventChoices(Event e){
        System.out.println("* * * * * *");
        System.out.println();
        System.out.println(e.getNarrative());
        System.out.println();

        for(int i = 0; i < 4; i++){
            displayOptionToChoose((Option) e.options.get(i), i+1);
        }
        System.out.println();
        System.out.println("Quel est votre choix ?");
        System.out.println();
        System.out.println("* * * * * *");
        System.out.println();
    }

    public void displayNotaNumber(){
        System.out.println("Le numéro n'est pas valide.");
    }

    public void wrongChoiceIndex(){
        System.out.println("Le numéro doit être entre 1 et 4.");
    }

    public void displayChoiceConsequence(Option option){
        System.out.println(option.getConsequence());
        System.out.println();
    }

    public void displayNewResources(Resources resources, Option option){
        System.out.println("Impact :");
        option.getFactionImpact().forEach((k, v) -> System.out.println(k + " : " + v));

        System.out.println("Trésor : " + option.getTreasuryImpact());
        System.out.println("Food : " + option.getFoodImpact());
        System.out.println("Industry : " + option.getIndustryImpact());
        System.out.println("Agriculture : " + option.getAgricultureImpact());
        System.out.println();
    }
}