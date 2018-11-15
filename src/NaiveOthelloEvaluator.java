public class NaiveOthelloEvaluator implements OthelloEvaluator {
    @Override
    public int evaluate(OthelloPosition position) {
        //naive count number of my colors.
        char playerColor = position.playerToMove ? 'W' : 'B';
        char enemyColor = position.playerToMove ? 'B' : 'W';
        int score = 0;
        if(position.getNumEmptySpaces() <= 8){
            for (int y = 1; y <= 8; y++) {
                for (int x = 1; x <= 8; x++) {
                    if(position.board[x][y] == playerColor){
                        score += 50;
                    }
                    if(position.board[x][y] == enemyColor){
                        score -= 50;
                    }
                }
            }
        } else{
            score += position.getMoves().size();
            for (int y = 1; y <= 8; y++) {
                for (int x = 1; x <= 8; x++) {
                    if(position.board[x][y] == 'E'){
                        if(position.enemyNeighbourExists(enemyColor, x, y)){
                            score++;
                        }
                    }
                }
            }
        }


        return score;
    }
}
