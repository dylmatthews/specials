package am.dx.varsityspecials.www.varsityspecials;

/**
 * Created by 15009851 on 2017-07-18.
 */

public class Card {
    private String line1;
    private String line2;
    private String line3;

    public Card(String name, String des, String time) {
        line1 = name;
        line2 = des;
        line3 = time;
    }
    public Card(String line1) {
        this.line1 = line1;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public String getLine3(){return line3;}

}