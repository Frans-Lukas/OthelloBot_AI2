public class NaiveOthelloEvaluator implements OthelloEvaluator {
    @Override
    public int evaluate(OthelloPosition position) {
        //naive count number of my colors.
        char myColor = position.playerToMove ? 'W' : 'B';
        int count = 0;
        for (char[] boardRows : position.board) {
            for (char boardPositionDisk : boardRows) {
                if(boardPositionDisk == myColor){
                    count++;
                }
            }
        }
        return count;
    }
}
