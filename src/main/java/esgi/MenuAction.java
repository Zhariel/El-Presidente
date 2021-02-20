package esgi;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MenuAction {

    private final Output out = new Output();
    private final Scanner sc = new Scanner(System.in);

    public MenuAction() {
    }

    public static class Market {
        public static final int cost = 8;
    }


    public void endTurn(Resources resource, ArrayList<Faction> factionsList) {
        out.suggestChoices();

        while (true) {
            int input = sc.nextInt();

            if (input == 1) {

                out.promptFaction();
                String faction = sc.next();

                try {
                    bribeAction(faction, resource, factionsList);
                } catch (NoSuchElementException e) {
                    out.displayInvalid();
                }

            } else if (input == 2) {

                out.promptFoodAmount();
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


    public void bribeAction(String faction, Resources resource, ArrayList<Faction> factionsList) throws NoSuchElementException {

        Faction factionToBribe = factionsList.stream()
                .filter(f -> f.getName().equals(faction))
                .findFirst()
                .get();

        if (factionToBribe.bribe(resource) && !factionToBribe.getName().equals("loyalistes")) {
            factionsList
                    .stream()
                    .filter(f -> f.getName().equals("loyalistes"))
                    .findFirst()
                    .get()
                    .deduceSatisfaction(factionToBribe.getPartisans() * 15 / 10);
        }
    }

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
