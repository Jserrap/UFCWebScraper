package ufc.fighter.scraper;

import org.apache.commons.text.WordUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ufc.fighter.scraper.model.Fighter;

import java.io.IOException;
import java.util.Scanner;

public class FighterScraper {

    private final Scanner scanner = new Scanner(System.in);
    private Fighter fighter;
    private String fighter_name;
    private Document document;

    public FighterScraper() {
        String again = "";
        while (!again.equals("0")){
            setURL();
            setStats();
            setFights();
            System.out.println(fighter);
            System.out.print("\nType 0 to leave: ");
            again = scanner.nextLine();
        }
    }

    private void setURL(){
        System.out.print("Write the fighter's name: ");
        fighter_name = scanner.nextLine().trim().replace(' ', '-').toLowerCase();
        String url = "https://www.ufc.com/athlete/" + fighter_name;
        try {
            this.document =  Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setStats(){
        String ranking = document.select("p.hero-profile__tag").getFirst().text();
        String name = document.select("h1.hero-profile__name").getFirst().text();
        String division = document.select("p.hero-profile__division-title").getFirst().text();
        String record = document.select("p.hero-profile__division-body").getFirst().text();
        String stats = document.select("div.hero-profile__stat").text();

        fighter = new Fighter(name, record, ranking, division);
    }

    private void setFights(){
        Elements fights = document.select(".c-card-event--athlete-results");

        for (Element fight: fights){
            Elements red_corner = fight.select("div.c-card-event--athlete-results__red-image");
            Elements blue_corner = fight.select("div.c-card-event--athlete-results__blue-image");

            String blue_link = blue_corner.select("a").getFirst().attr("href");
            String blue_fighter = blue_link.substring(blue_link.lastIndexOf('/') + 1)
                    .replace('-', ' ');
            blue_fighter = WordUtils.capitalize(blue_fighter);

            String red_link = red_corner.select("a").getFirst().attr("href");
            String red_fighter = red_link.substring(red_link.lastIndexOf('/') + 1)
                    .replace('-', ' ');
            red_fighter = WordUtils.capitalize(red_fighter);

            if (red_corner.hasClass("win")) {
                fighter.addFight(red_fighter, blue_fighter, red_fighter);
            } else if (red_corner.hasClass("loss")) {
                fighter.addFight(red_fighter, blue_fighter, blue_fighter);
            } else {
                fighter.addFight(red_fighter, blue_fighter, "NC");
            }
        }
    }

    private void setResult(){

    }
}
