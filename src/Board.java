/**
 * Output class
 * @author Melanie Krugel 198991, Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 3.0 30.03.2018
 */
public class Board {

    //TODO find better way than HTML
    public static String draw(Player p1, Player p2, Player currentPlayer, Matrix<Fraction> mat) {
        StringBuilder s = new StringBuilder();
        s.append(String.join(
                "<br/>",
                playerScore(p1),
                playerScore(p2),
                currentPlayerInfo(currentPlayer),
                "<br/>"
        ));

        for(int y = MAX.START_Y; y <= MAX.END_Y; y++) {
            for(int x = MAX.START_X; x <= MAX.END_X; x++) {
                if(p1.position.x == x && p1.position.y == y) {
                    s.append(stringforamtter(p1.getShortName()));
                } else if( p2.position.x == x && p2.position.y == y) {
                    s.append(stringforamtter(p2.getShortName()));
                } else {
                    s.append(stringforamtter(mat.getValue(x,y).toString()));

                }
            }
            s.append("<br/>");
        }
        return "<html>"+s.toString()+"</html>";
    }
    public static String stringforamtter(String s) {
        if (s.length() == 1) {

            return "|"+"&nbsp;"+"&nbsp;" + s +"&nbsp;"+"&nbsp;"+ "|";
        }

        if (s.length() == 3) {

            return ("| " + s + " |");

        }
        if (s.length() == 4) {
           // String[] split = s.split("/");
            //if (split[0].length() == 1) s = " " + s;
            //if (split[1].length() == 1) s = s + " ";
            return "|"+s+" |";
        }
        if(s.length()==2){

            return "|  "+s+" |";
        }
        return "|"+s+"|";
    }
    private static String playerScore(Player p){
        return p.getName() + " Score: " + p.score.floatValue();
    }

    private static String currentPlayerInfo(Player p){
        return "Current Player: " + p.getName();
    }
}
