public class Main {

    public static void main(String[] args) {
        OthelloPosition pos = new OthelloPosition();
        pos.initialize();
        MyOthelloAlgorithm solver = new MyOthelloAlgorithm();
        while(solver.gameIsPlayable(pos)){
            try {
                OthelloAction actionToDo = solver.evaluate(pos);
                String playerColor = pos.playerToMove ? "White" : "Black";
                if(actionToDo.isPassMove()){
                    System.out.println(playerColor + " is passing.");
                } else{
                    System.out.println(playerColor + " is making a move on pos X: " + actionToDo.column + ", Y: " + actionToDo.row);
                }
                pos.makeMove(actionToDo);
                pos.illustrate();

            } catch (IllegalMoveException e) {
                e.printStackTrace();
            }
        }

        int whiteScore = 0;
        int blackScore = 0;
        for (char[] chars : pos.board) {
            for (char aChar : chars) {
                if(aChar == 'W'){
                    whiteScore++;
                } else if(aChar == 'B'){
                    blackScore++;
                }
            }
        }

        System.out.println("White got score: " + whiteScore);
        System.out.println("Black got score: " + blackScore);
    }
}
