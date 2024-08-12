package ufc.fighter.scraper.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class Fighter {
    private String name;
    private String record;
    private String ranking;
    private String division;
    private List<Fight> lutas = new ArrayList<>();

    public Fighter(String name, String record, String ranking, String division) {
        this.name = name;
        this.record = record;
        this.ranking = ranking;
        this.division = division;
    }

    public void addFight(String red_corner, String blue_corner, String winner){
        lutas.add(new Fight(red_corner, blue_corner, winner));
    }

    public List<Fight> getLutas() {
        return lutas;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
