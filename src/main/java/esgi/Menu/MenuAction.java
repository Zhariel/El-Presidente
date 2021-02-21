package esgi.Menu;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import esgi.Entities.*;
import esgi.Display.Output;

public class MenuAction {

    private final Output out = new Output();
    private final Scanner sc = new Scanner(System.in);

    public MenuAction() {
    }

    public static class Market {
        public static final int cost = 8;
    }


    /**
     * called when the turn has to end
     * gets input from the user
     * lets the user bribe a faction, buy food on the market, or end the turn
     * @param resource
     * @param factionsList
     */
    public void endTurn(Resources resource, ArrayList<Faction> factionsList) {
        out.suggestChoices();

        while (true) {
            int input = sc.nextInt();

            if (input == 1) {

                out.promptFaction();
                String faction = sc.next();

                try {
                    bribeAction(faction, resource, factionsList);
                } catch (Exception e) {
                    out.displayInvalid();
                }

            } else if (input == 2) {

                out.promptFoodAmount(resource.getTreasury());
                int amount = sc.nextInt();
                buyFoodAction(resource, amount);

            } else if (input == 3) {
                break;

            } else {
                out.displayInvalid();
            }
            out.suggestChoices();
        }
    }

    /**
     * finds the faction to bribe accoring to the string
     * @param faction
     * @param factionsList
     * @return
     */
    public Faction findFactionToBribe(String faction, ArrayList<Faction> factionsList){
        return factionsList
                .stream()
                .filter(f -> f.getName().equals(faction))
                .findFirst()
                .get();
    }

    /**
     * bribes the faction
     * and if they are not the loyalists,
     * deduce satisfaction from the loyalists.
     * @param faction
     * @param resource
     * @param factionsList
     * @throws NoSuchElementException
     */
    public void bribeAction(String faction, Resources resource, ArrayList<Faction> factionsList) throws NoSuchElementException {

        Faction factionToBribe = findFactionToBribe(faction, factionsList);

        if (factionToBribe.bribe(resource) && !factionToBribe.isFaction("loyalistes")) {
            findFactionToBribe("loyalistes", factionsList)
                .deduceSatisfaction(factionToBribe.getPartisans() * 15 / 10);
        }
    }

    /**
     * if there is the money, buys food, else does nothing
     * @param resource
     * @param amount
     */
    public void buyFoodAction(Resources resource, int amount) {
        int totalCost = amount * Market.cost;

        if (resource.canBuy(totalCost)) {
            resource.addFood(amount);
            resource.deduceTreasury(totalCost);
            out.boughtFood(resource, amount, totalCost);
        } else {
            out.cannotBuyFood();
        }
    }
}
