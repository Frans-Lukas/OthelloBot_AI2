import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyOthelloAlgorithm implements OthelloAlgorithm {
    private OthelloEvaluator evaluator;
    private int maxDepth = 7;

    public MyOthelloAlgorithm() {
        evaluator = new NaiveOthelloEvaluator();
    }

    @Override
    public void setEvaluator(OthelloEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public OthelloAction evaluate(OthelloPosition position) {
        ArrayList<OthelloAction> actions = position.getMoves();
        if(actions.size() == 0){
            OthelloAction passingAction = new OthelloAction(0,0);
            passingAction.pass = true;
            return passingAction;
        }
        for (int i = 0; i < actions.size(); i++) {
            OthelloPosition posToMaybeMake = position.clone();
            try {
                posToMaybeMake.makeMove(actions.get(i));
                actions.get(i).setValue(alphaBeta(posToMaybeMake));
            } catch (IllegalMoveException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(actions, Comparator.comparingInt(OthelloAction::getValue));

        return actions.get(actions.size() - 1);
    }

    @Override
    public void setSearchDepth(int depth) {

    }

    public int alphaBeta(OthelloPosition pos) throws IllegalMoveException {
        return maxValue(pos, -100, 100, 0);
    }

    public boolean gameIsPlayable(OthelloPosition playerTurn){
        OthelloPosition oppositePlayerTurn = playerTurn.clone();
        oppositePlayerTurn.playerToMove = !oppositePlayerTurn.playerToMove;
        return playerTurn.canMakeMove() && oppositePlayerTurn.canMakeMove();
    }

    private int maxValue(OthelloPosition pos, int alpha, int beta, int currentDepth) throws IllegalMoveException {
        if(currentDepth >= maxDepth || !gameIsPlayable(pos)){
            return evaluator.evaluate(pos);
        }
        int value = -100;
        ArrayList<OthelloAction> actions = pos.getMoves();
        for (OthelloAction action : actions) {
            OthelloPosition childMoves = pos.clone();
            childMoves.makeMove(action);
            value = Math.max(value, minValue(childMoves, alpha, beta, currentDepth + 1));
            alpha = Math.max(alpha, value);
            if(alpha >= beta){
                break;
            }
        }
        return value;
    }

    private int minValue(OthelloPosition pos, int alpha, int beta, int currentDepth) throws IllegalMoveException {
        if(currentDepth >= maxDepth || !gameIsPlayable(pos)){
            return evaluator.evaluate(pos);
        }
        int value = 100;
        ArrayList<OthelloAction> actions = pos.getMoves();
        for (OthelloAction action : actions) {
            value = Math.min(value, maxValue(pos, alpha, beta, currentDepth + 1));
            beta = Math.min(beta, value);
            if(alpha >= beta){
                break;
            }
        }
        return value;
    }

}
