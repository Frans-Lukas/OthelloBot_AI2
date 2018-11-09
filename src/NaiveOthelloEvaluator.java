public class NaiveOthelloEvaluator implements OthelloEvaluator {
    @Override
    public int evaluate(OthelloPosition position) {
        //naive count number of my colors.
        char myColor = position.playerToMove ? 'W' : 'B';
        int playerCount = 0;
        int enemyCount = 0;
        for (char[] boardRows : position.board) {
            for (char boardPositionDisk : boardRows) {
                if(boardPositionDisk == myColor){
                    playerCount++;
                } else if(boardPositionDisk != 'E'){
                    enemyCount++;
                }
            }
        }
        return playerCount - enemyCount;
    }
}
