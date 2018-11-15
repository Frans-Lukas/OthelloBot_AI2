import java.util.ArrayList;
import java.util.Comparator;

public class MyOthelloAlgorithm implements OthelloAlgorithm {
    private OthelloEvaluator evaluator;
    private int maxDepth = 8;
    private long timeLimit;
    private long currTime;

    public MyOthelloAlgorithm(int timeLimit) {
        evaluator = new NaiveOthelloEvaluator();
        this.timeLimit = timeLimit;
    }

    @Override
    public void setEvaluator(OthelloEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public OthelloAction evaluate(OthelloPosition position) {
        ArrayList<OthelloAction> actions = position.getMoves();
        currTime = System.nanoTime();

        if(actions.size() == 0){
            OthelloAction passingAction = new OthelloAction(0,0);
            passingAction.pass = true;
            return passingAction;
        }
        maxDepth = 8;
        while(isBelowTimeLimit()){
            maxDepth++;
            actions.stream().parallel().forEach(action -> {
                OthelloPosition posToMaybeMake = position.clone();
                try {
                    posToMaybeMake.makeMove(action);
                    int value = alphaBeta(posToMaybeMake);
                    if(isBelowTimeLimit()){
                        action.setValue(value);
                    }
                } catch (IllegalMoveException e) {
                    e.printStackTrace();
                }
            });
        }
        actions.sort((x,y) -> Integer.compare(x.getValue(), y.getValue()) * -1);
        System.err.println("max depth: " + maxDepth);
        System.err.println("value: " + actions.get(0).getValue());
        return actions.get(0);
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
        return playerTurn.canMakeMove() || oppositePlayerTurn.canMakeMove();
    }

    boolean isBelowTimeLimit(){
        return System.nanoTime() - currTime < (timeLimit)* 1000000000;
    }

    private int maxValue(OthelloPosition pos, int alpha, int beta, int currentDepth) throws IllegalMoveException {
        if(currentDepth >= maxDepth || !gameIsPlayable(pos) || !isBelowTimeLimit()){
            return evaluator.evaluate(pos);
        }
        int value = Integer.MIN_VALUE;
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
        if(currentDepth >= maxDepth || !isBelowTimeLimit() || !pos.canMakeMove()){
            return evaluator.evaluate(pos);
        }
        int value = Integer.MAX_VALUE;
        ArrayList<OthelloAction> actions = pos.getMoves();
        for (OthelloAction action : actions) {
            OthelloPosition childMoves = pos.clone();
            childMoves.makeMove(action);
            value = Math.min(value, maxValue(childMoves, alpha, beta, currentDepth + 1));
            beta = Math.min(beta, value);
            if(alpha >= beta){
                break;
            }
        }
        return value;
    }

}
