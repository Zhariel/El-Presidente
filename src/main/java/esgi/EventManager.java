package esgi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class EventManager {

    private final int eventRate;
    private final ObjectMapper mapper = new ObjectMapper();
    private final File file;

    public EventManager(){
        this.file = (new File("src\\main\\java\\esgi\\json\\" + "events.json")).getAbsoluteFile();
        eventRate = 2;
    }

    void diceRoll(String season){
        Random rand = new Random();
        int num = rand.nextInt(eventRate);

        if(num == eventRate-1){
            season = rand.nextInt(2) == 1 ? "misc" : season;
            int index = rand.nextInt(4);

            playEvent(season, index);
        }
    }

    void playEvent(String season, int index){
        System.out.println("playing " + season + " " + index);
        try {
            ExtractEventNode(season, index);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ObjectNode ExtractEventNode(String season, int seasonIndex) throws IOException{

        ArrayNode seasonArray = (ArrayNode)mapper.readTree(file);
        return (ObjectNode)seasonArray.get(season).get(seasonIndex);
    }

    public Event initEvent(String season, int seasonIndex) throws IOException{

        ObjectNode eventNode = ExtractEventNode(season, seasonIndex);
        ArrayList optionArray = new ArrayList();

        for(JsonNode node : eventNode.get("choices")){
            JsonNode n = node.get("impact");
            Option option = new Option(
                    n.get("decision").toString().replace("\"", ""),
                    n.get("consequence").toString().replace("\"", ""),
                    n.get("treasury").intValue(),
                    n.get("food").intValue());

            for(JsonNode m : n.get("factions")){
                option.factionImpact.put("name", m.get("name").toString().replace("\"", ""));
                option.factionImpact.put("effect", m.get("effect").intValue());
            }

            optionArray.add(option);
        }

        return new Event(
                eventNode.get("name").toString().replace("\"", ""),
                seasonIndex,
                eventNode.get("narrative").toString().replace("\"", ""),
                optionArray
        );
    }
}
