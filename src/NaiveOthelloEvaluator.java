public class NaiveOthelloEvaluator implements OthelloEvaluator {
    @Override
    public int evaluate(OthelloPosition position) {
        if(position.getNumEmptySpaces() > 3){
            char playerColor = position.playerToMove ? 'W' : 'B';
            char enemyColor = position.playerToMove ? 'B' : 'W';
            int score = 0;
            //https://github.com/hylbyj/Alpha-Beta-Pruning-for-Othello-Game/blob/master/readme_alpha_beta.txt
            int diskScoreBoard[][] = {{8, -3, 2, 2, 2, 2, -3, 8},
                                    {-3, -4, -1, -1, -1, -1, -4, -3},
                                    {2, -1, 1, 0, 0, 1, -1, 2},
                                    {2, -1, 0, 1, 1, 0, -1, 2},
                                    {2, -1, 0, 1, 1, 0, -1, 2},
                                    {2, -1, 1, 0, 0, 1, -1, 2},
                                    {-3, -4, -1, -1, -1, -1, -4, -3},
                                    {8, -3, 2, 2, 2, 2, -3, 8}};
            for (int y = 1; y <= 8; y++) {
                for (int x = 1; x <= 8; x++) {
                    if(position.board[x][y] == playerColor){
                        score += diskScoreBoard[x - 1][y - 1];
                    }
                }
            }
            return score;
        } else{
            return countPointDifference(position) * 1000;
        }
//        if(position.getNumEmptySpaces() >= 2){
//            return heuristicMobility(position) + heuristicPotentialMobility(position);
//        }
//        return heuristicDiskParity(position);
    }

    public int heuristicDiskParity(OthelloPosition position){
        char playerColor = position.playerToMove ? 'W' : 'B';
        char enemyColor = position.playerToMove ? 'B' : 'W';
        int playerScore = 0;
        int enemyScore = 0;
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                if(position.board[x][y] == playerColor){
                    playerScore += 1;
                } else if(position.board[x][y] == enemyColor){
                    enemyScore += 1;
                }
            }
        }
        return 100 * (playerScore - enemyScore) / (playerScore + enemyScore);
    }

    public int heuristicMobility(OthelloPosition position){
        char playerColor = position.playerToMove ? 'W' : 'B';
        char enemyColor = position.playerToMove ? 'B' : 'W';
        int playerMoves = position.getMoves().size();
        position.playerToMove = !position.playerToMove;
        int enemyMoves = position.getMoves().size();
        if(playerMoves + enemyMoves > 0){
            return 100 * (playerMoves - enemyMoves) / (playerMoves + enemyMoves);
        } else{
            return 0;
        }
    }

    public int heuristicNumCorners(OthelloPosition position){
        char playerColor = position.playerToMove ? 'W' : 'B';
        char enemyColor = position.playerToMove ? 'B' : 'W';
        int maxScore = 0;
        int minScore = 0;

        if(position.board[1][1] == playerColor) maxScore++;
        if(position.board[1][8] == playerColor) maxScore++;
        if(position.board[8][8] == playerColor) maxScore++;
        if(position.board[8][1] == playerColor) maxScore++;

        if(position.board[1][8] == enemyColor) minScore++;
        if(position.board[8][8] == enemyColor) minScore++;
        if(position.board[8][1] == enemyColor) minScore++;
        if(position.board[8][1] == enemyColor) minScore++;

        if(maxScore + minScore > 0){
            return 100 * (maxScore - minScore) / (maxScore + minScore);
        } else{
            return 0;
        }
    }

    public int heuristicStability(OthelloPosition position){
        return 0;
    }

    public int heurisiticCornerCloseness(OthelloPosition position){
        char playerColor = position.playerToMove ? 'W' : 'B';
        char enemyColor = position.playerToMove ? 'B' : 'W';

        int maxScore = 0;
        int minScore = 0;

        char board[][] = position.board;
        if(position.board[1][1] == 'E'){
            if(board[2][2] == playerColor) maxScore++;
            else if(board[2][2] == enemyColor) minScore++;
            if(board[1][2] == playerColor) maxScore++;
            else if(board[1][2] == enemyColor) minScore++;
            if(board[2][1] == playerColor) maxScore++;
            else if(board[2][1] == enemyColor) minScore++;
        }
        if(position.board[1][8] == 'E'){
            if(board[2][7] == playerColor) maxScore++;
            else if(board[2][7] == enemyColor) minScore++;
            if(board[2][8] == playerColor) maxScore++;
            else if(board[2][8] == enemyColor) minScore++;
            if(board[1][7] == playerColor) maxScore++;
            else if(board[1][7] == enemyColor) minScore++;

        }
        if(position.board[8][8] == 'E'){
            if(board[7][7] == playerColor) maxScore++;
            else if(board[7][7] == enemyColor) minScore++;
            if(board[7][8] == playerColor) maxScore++;
            else if(board[7][8] == enemyColor) minScore++;
            if(board[8][7] == playerColor) maxScore++;
            else if(board[8][7] == enemyColor) minScore++;

        }
        if(position.board[8][1] == 'E'){
            if(board[7][2] == playerColor) maxScore++;
            else if(board[7][2] == enemyColor) minScore++;
            if(board[8][2] == playerColor) maxScore++;
            else if(board[8][2] == enemyColor) minScore++;
            if(board[7][1] == playerColor) maxScore++;
            else if(board[7][1] == enemyColor) minScore++;
        }
        if(maxScore + minScore == 0){
            return 0;
        }
        return -100 * (maxScore - minScore) / (maxScore + minScore);
    }

    public int heuristicPotentialMobility(OthelloPosition position){
        char playerColor = position.playerToMove ? 'W' : 'B';
        char enemyColor = position.playerToMove ? 'B' : 'W';
        int max = 0;
        int min = 0;
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                if(position.board[x][y] == 'E'){
                    if(position.enemyNeighbourExists(enemyColor, x, y)){
                        max++;
                    }
                    if(position.enemyNeighbourExists(playerColor, x, y)){
                        min++;
                    }
                }
            }
        }
        if(max + min == 0){
            return 0;
        }
        return 100 * (max - min) / (max + min);
    }

    public int countPotentialMobility(OthelloPosition position){
        int score = 0;
        char playerColor = position.playerToMove ? 'W' : 'B';
        char enemyColor = position.playerToMove ? 'B' : 'W';
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                if(position.board[x][y] == 'E'){
                    if(position.enemyNeighbourExists(enemyColor, x, y)){
                        score++;
                    }
                }
            }
        }
        return score;
    }

    public int countMyMobility(OthelloPosition position){
        return position.getMoves().size();
    }

    public int countCorners(OthelloPosition position){
        char playerColor = position.playerToMove ? 'W' : 'B';
        char enemyColor = position.playerToMove ? 'B' : 'W';
        int score = 0;

        if(position.board[1][1] == playerColor) score++;
        if(position.board[1][8] == playerColor) score++;
        if(position.board[8][8] == playerColor) score++;
        if(position.board[8][1] == playerColor) score++;

        return score;
    }

    public int countPointDifference(OthelloPosition position){
        char playerColor = position.playerToMove ? 'W' : 'B';
        char enemyColor = position.playerToMove ? 'B' : 'W';
        int score = 0;
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                if(position.board[x][y] == playerColor){
                    score += 1;
                }
                if(position.board[x][y] == enemyColor){
                    score -= 1;
                }
            }
        }

        return score;
    }

    public int countXSquares(OthelloPosition position){
        char playerColor = position.playerToMove ? 'W' : 'B';
        char enemyColor = position.playerToMove ? 'B' : 'W';
        int score = 0;
        if(position.board[2][2] == playerColor) score++;
        if(position.board[2][7] == playerColor) score++;
        if(position.board[7][2] == playerColor) score++;
        if(position.board[7][7] == playerColor) score++;
        return score;
    }
}
