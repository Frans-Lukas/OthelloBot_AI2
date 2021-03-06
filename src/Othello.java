public class Othello {

    public static void main(String[] args) {

        if(args.length != 2 || args[0].length() != 65){
            throw new IllegalArgumentException("Arguments need to be playerColorBoard timelimit");
        }

        OthelloPosition pos = new OthelloPosition(args[0]);
        int timeLimit = Integer.parseInt(args[1]);
        MyOthelloAlgorithm solver = new MyOthelloAlgorithm(timeLimit);
        OthelloAction actionToDo = solver.evaluate(pos);
        String playerColor = pos.playerToMove ? "White" : "Black";
        if(actionToDo.isPassMove()){
            System.out.println("pass");
        } else{
            System.out.println("(" + actionToDo.getRow() + "," + actionToDo.getColumn() + ")");
        }
        //pos.illustrate();
    }
}
