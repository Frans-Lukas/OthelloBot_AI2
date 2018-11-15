public class NaiveOthelloEvaluator implements OthelloEvaluator {
    @Override
    public int evaluate(OthelloPosition position) {
        //naive count number of my colors.
        char playerColor = position.playerToMove ? 'W' : 'B';
        char enemyColor = position.playerToMove ? 'B' : 'W';
        int playerCount = 0;
        int enemyCount = 0;
        playerCount = position.getMoves().size();
        position.playerToMove = !position.playerToMove;
        //enemyCount = position.getMoves().size();
        int score = 0;



        return score;
    }
}
