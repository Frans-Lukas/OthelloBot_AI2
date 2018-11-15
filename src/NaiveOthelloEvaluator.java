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
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if(position.board[i][j] == playerColor){
                    score++;
                } else if(position.board[i][j] == enemyColor){
                    score--;
                }
            }
        }

        if(position.board[1][1] == playerColor){
            score += 500;
        }
        if(position.board[8][8] == playerColor){
            score += 500;
        }
        if(position.board[1][8] == playerColor){
            score += 500;
        }
        if(position.board[8][1] == playerColor){
            score += 500;
        }

        if(position.board[1][1] == enemyColor){
            score -= 500;
        }
        if(position.board[8][8] == enemyColor){
            score -= 500;
        }
        if(position.board[1][8] == enemyColor){
            score -= 500;
        }
        if(position.board[8][1] == enemyColor){
            score -= 500;
        }


        return score;
    }
}
