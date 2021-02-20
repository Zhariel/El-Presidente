package esgi;

import java.util.ArrayList;

public class Event {
    private String season;
    private int index;
    private String narrative;
    public ArrayList options;

    public Event(String season, int index, String narrative, ArrayList options) {
        this.season = season;
        this.index = index;
        this.narrative = narrative;
        this.options = options;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public ArrayList getOptions() {
        return options;
    }

    public void setOptions(ArrayList options) {
        this.options = options;
    }

    public boolean isChosen(String seasonChosen, int indexChosen){
        return seasonChosen.equals(season) && indexChosen == index;
    }
}
