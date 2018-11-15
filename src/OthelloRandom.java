import java.util.ArrayList;
import java.util.Random;

public class OthelloRandom {
    public static void main(String[] args) {
        OthelloPosition pos = new OthelloPosition(args[0]);
        ArrayList<OthelloAction> actions = pos.getMoves();
        if(actions.size() == 0){
            System.out.println("pass");
            return;
        }

        Random rand = new Random();
        OthelloAction actionToDo = actions.get(rand.nextInt(actions.size()));
        System.out.println("(" + actionToDo.getRow() + "," + actionToDo.getColumn() + ")");
    }
}
