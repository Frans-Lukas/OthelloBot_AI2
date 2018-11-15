import java.util.ArrayList;

public class MyOthelloAlgorithm implements OthelloAlgorithm {
    private OthelloEvaluator evaluator;
    private int currMaxDepth;
    private int maxDepth = 4;
    private long timeLimit;
    private long currTime;
    private boolean reachedMaxDepth = false;

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

        currMaxDepth = maxDepth;
        while(isBelowTimeLimit()){
            currMaxDepth++;
            reachedMaxDepth = false;
            for (OthelloAction action : actions) {
                try {
                    OthelloPosition posToMaybeMake = position.clone();
                    posToMaybeMake.makeMove(action);
                    int value = alphaBeta(posToMaybeMake);
                    if(isBelowTimeLimit()){
                        action.setValue(value);
                    }
                } catch (IllegalMoveException e) {
                    e.printStackTrace();
                }
            }
        }
        actions.sort((x,y) -> Integer.compare(x.getValue(), y.getValue()) * -1);
        System.err.println("max depth: " + currMaxDepth);
        System.err.println("value: " + actions.get(0).getValue() + " worst value: " + actions.get(actions.size() - 1).getValue());
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

    private boolean isBelowTimeLimit(){
        return System.nanoTime() - currTime < (timeLimit)* 1000000000;
    }

    private int maxValue(OthelloPosition pos, int alpha, int beta, int currentDepth) throws IllegalMoveException {
        if(isLeaf(pos, currentDepth)){
            return evaluator.evaluate(pos);
        }
        int value = Integer.MIN_VALUE;

        ArrayList<OthelloAction> actions = pos.getMoves();
        for (OthelloAction action : actions) {
            OthelloPosition tempPos = pos.clone();
            tempPos.makeMove(action);
            value = Math.max(value, minValue(tempPos, alpha, beta, currentDepth + 1));
            alpha = Math.max(alpha, value);
            if(alpha >= beta){
                break;
            }
        }
        return value;
    }

    private int minValue(OthelloPosition pos, int alpha, int beta, int currentDepth) throws IllegalMoveException {
        if(isLeaf(pos, currentDepth)){
            return evaluator.evaluate(pos);
        }
        int value = Integer.MAX_VALUE;

        ArrayList<OthelloAction> actions = pos.getMoves();
        for (OthelloAction action : actions) {
            OthelloPosition tempPos = pos.clone();
            tempPos.makeMove(action);
            value = Math.min(value, maxValue(tempPos, alpha, beta, currentDepth + 1));
            beta = Math.min(beta, value);
            if(alpha >= beta){
                break;
            }
        }
        return value;
    }

    private boolean isLeaf(OthelloPosition pos, int currentDepth) {
        return currentDepth >= currMaxDepth || !isBelowTimeLimit() || !pos.canMakeMove();
    }

}
