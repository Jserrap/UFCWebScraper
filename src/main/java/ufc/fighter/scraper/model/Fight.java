package ufc.fighter.scraper.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Fight {
    private String red_corner;
    private String blue_corner;
    private String winner;

    public Fight(String red_corner, String blue_corner, String winner) {
        this.red_corner = red_corner;
        this.blue_corner = blue_corner;
        this.winner = winner;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
