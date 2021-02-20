package esgi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EventManager {

    private final ObjectMapper mapper = new ObjectMapper();
    private final File file = (new File("src\\main\\java\\esgi\\json\\" + "events.json")).getAbsoluteFile();
    private final Output out = new Output();
    private final Scanner scanner = new Scanner(System.in);

    public EventManager() {}

    public int diceRoll(String season) {
        Random rand = new Random();
        int eventRate = 2;
        int num = rand.nextInt(eventRate);

        if (num == eventRate - 1) {
            season = rand.nextInt(5) == 1 ? "misc" : season;
            int index = rand.nextInt(2);

            return index;
        }
        return -1;
    }

    public ObjectNode ExtractEventNode(String season, int choiceIndex) throws IOException {
        ArrayNode seasonArray = (ArrayNode) mapper.readTree(file);

        int index = 0;
        if (seasonArray.isArray()) {
            for (JsonNode n : seasonArray) {
                if ((n.get("name").toString().replace("\"", "")).equals(season)) {
                    break;
                }
                index++;
            }

        }

        return (ObjectNode) seasonArray.get(index).get("scenario").get(choiceIndex);
    }

    public Event initEvent(String season, int choiceIndex) throws IOException {

        ObjectNode eventNode = ExtractEventNode(season, choiceIndex);
        ArrayList optionArray = new ArrayList();

        for (JsonNode node : eventNode.get("choices")) {

            String decision = node.get("decision").toString().replace("\"", "");
            String consequence = node.get("consequence").toString().replace("\"", "");
            JsonNode n = node.get("impact");

            Option option = new Option(
                    decision,
                    consequence,
                    n.get("treasury").intValue(),
                    n.get("food").intValue(),
                    n.get("industry").intValue(),
                    n.get("agriculture").intValue());

            for (JsonNode m : n.get("factions")) {
                option.getFactionImpact().put(m.get("name").toString().replace("\"", ""), m.get("effect").intValue());
            }

            optionArray.add(option);
        }

        return new Event(
                season,
                choiceIndex,
                eventNode.get("narrative").toString().replace("\"", ""),
                optionArray
        );
    }

    public ArrayList<Event> populateEventList(HashMap<String, String> seasonMap)throws IOException{
        ArrayList<Event> seasonList = new ArrayList<Event>();

        seasonMap.forEach((k, v) -> {
            try {
                seasonList.add(initEvent(k.toString(), 0));
                seasonList.add(initEvent(k.toString(), 1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return seasonList;
    }

    public boolean isAcceptableInput(int solution){
        return solution > 0 && solution < 5;
    }

    public Event findSelectedEvent(ArrayList<Event> eventList, String season, int index){
        return eventList.stream()
                .filter(e -> e.isChosen(season, index))
                .findFirst()
                .get();
    }

    public Option inputChoiceChosen(Event event) {

        //out.debugEvent(event, false);
        out.displayEventChoices(event);

        String solution = "";
        int chosen;
        while(true){
            solution = scanner.next();
            try{
                chosen = Integer.parseInt(solution);
            }
            catch(NumberFormatException e){
                out.displayNotaNumber();
                continue;
            }

            if(!isAcceptableInput(chosen)){
                out.wrongChoiceIndex();
            }
            else break;
        }

        return (Option) event.options.get(chosen-1);
    }


    public boolean applyChoice(int choiceIndex, String season, ArrayList<Event> eventList, Resources resources, ArrayList<Faction> factions){
        if(choiceIndex == -1) return false;

        Event event = findSelectedEvent(eventList, season, choiceIndex);
        Option option = inputChoiceChosen(event);

        //out.debugOption(option);
        out.displayChoiceConsequence(option);
        out.displayNewResources(resources, option);

        option.getFactionImpact().forEach((k, v) -> applyFactionStatChange(factions, (String)k, (Integer)v));


        resources.addTreasury(option.getTreasuryImpact());
        resources.addFood(option.getFoodImpact());
        resources.addAgriculture(option.getAgricultureImpact());
        resources.addIndustry(option.getIndustryImpact());

        return true;
    }

    public void applyFactionStatChange(ArrayList<Faction> factions, String faction, int value){
        factions.stream()
                .filter(f -> f.getName().equals(faction))
                .forEach(f -> f.addSatisfaction(value));
    }
}
