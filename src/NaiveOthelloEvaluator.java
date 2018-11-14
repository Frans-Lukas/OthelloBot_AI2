public class NaiveOthelloEvaluator implements OthelloEvaluator {
    @Override
    public int evaluate(OthelloPosition position) {
        //naive count number of my colors.
        char myColor = position.playerToMove ? 'W' : 'B';
        int playerCount = 0;
        int enemyCount = 0;
        for (int i = 1; i <= OthelloPosition.BOARD_SIZE; i++) {
            for (int j = 1; j <= OthelloPosition.BOARD_SIZE; j++) {
                if(position.board[j][i] == myColor){
                    playerCount++;
                } else if(position.board[j][i] != 'E'){
                    enemyCount++;
                }
            }
        }
        return playerCount - enemyCount;
    }
}
