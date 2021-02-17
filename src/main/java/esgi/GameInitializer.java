package esgi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameInitializer {
    private String config;
    private ObjectMapper mapper = new ObjectMapper();
    private File file;

    public GameInitializer(String config) {
        this.config = config;
        this.file = (new File("src\\main\\java\\esgi\\json\\" + "config.json")).getAbsoluteFile();
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public ObjectNode extractConfigObject() throws IOException{

        ArrayNode array = (ArrayNode)mapper.readTree(file);

        int index = 0;
        if(array.isArray()){
            for(JsonNode n : array){
                if((n.get("name").toString().replace("\"", "")).equals(config)){
                    break;
                }
                index++;
            }
        }

        return mapper.valueToTree(array.get(index));
    }

    public Resources initResources() throws IOException {

        ObjectNode configNode = extractConfigObject();

        ArrayNode factions = mapper.valueToTree(configNode.get("factions"));

        Resources res = new Resources(
                configNode.get("treasury").intValue(),
                0,
                (configNode.get("industry").intValue()),
                configNode.get("agriculture").intValue());

        return res;
    }

    public ArrayList<Faction> initFactions() throws IOException{
        ArrayList<Faction> factions = new ArrayList();

        ObjectNode configNode = extractConfigObject();

        for(JsonNode n : configNode.get("factions")){
            factions.add(new Faction(
                    n.get("name").toString().replace("\"", ""),
                    n.get("satisfaction").intValue(),
                    n.get("partisans").intValue()
            ));
        }

        return factions;
    }
}









