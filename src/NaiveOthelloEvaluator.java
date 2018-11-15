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
        enemyCount = position.getMoves().size();
        int score = -enemyCount;


        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j < 8; j++) {
                if(i != 0 && i != 8 && j > 1 && j < 8){
                    continue;
                }

            }
        }

        return -enemyCount;
    }
}
