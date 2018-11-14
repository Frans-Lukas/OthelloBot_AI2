import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class OthelloTests {

    @Test
    public void insideBoundsShouldReturnTrueWhenOutOfBounds(){
        OthelloPosition pos = new OthelloPosition();
        assertFalse(pos.inside_bounds(9, 9));
        assertFalse(pos.inside_bounds(7, 9));
        assertFalse(pos.inside_bounds(9, 7));
    }

    @Test
    public void insideBoundsShouldReturnTrueOnValidPosition(){
        OthelloPosition pos = new OthelloPosition();
        assertTrue(pos.inside_bounds(8, 8));
        assertTrue(pos.inside_bounds(1, 1));
        assertTrue(pos.inside_bounds(5, 6));
    }

    @Test
    public void numPointsForActionShouldReturnOneOnStarterBoard(){
        OthelloPosition pos = new OthelloPosition();
        pos.initialize();
        OthelloAction firstAction = new OthelloAction(6,4);
        assertEquals(pos.numPointsFor(firstAction), 1);
        firstAction = new OthelloAction(4,6);
        assertEquals(pos.numPointsFor(firstAction), 1);

        pos.board[4][6] = 'B';
        firstAction = new OthelloAction(4,7);
        assertEquals(pos.numPointsFor(firstAction), 2);
    }

    @Test
    public void numPointsForActionShouldReturnZeroOnStarterBoard(){
        OthelloPosition pos = new OthelloPosition();
        pos.initialize();

        OthelloAction firstAction = new OthelloAction(6,6);
        assertEquals(pos.numPointsFor(firstAction), 0);

        firstAction = new OthelloAction(6,5);
        assertEquals(pos.numPointsFor(firstAction), 0);

        pos.board[4][4] = 'B';
        firstAction = new OthelloAction(4,7);
        assertEquals(pos.numPointsFor(firstAction), 0);
    }

    @Test
    public void moveShouldFailOnInvalidAction(){
        OthelloPosition pos = new OthelloPosition();
        pos.initialize();

        OthelloAction firstAction = new OthelloAction(6,6);

        assertThrows(IllegalMoveException.class, () -> pos.makeMove(firstAction));
    }



    @Test
    public void moveShouldsucceedOnInvalidAction(){
        OthelloPosition pos = new OthelloPosition();
        pos.initialize();

        OthelloAction firstAction = new OthelloAction(6,4);

        assertDoesNotThrow(() -> pos.makeMove(firstAction));
    }


    @Test
    public void correctValidMovesAreGiven() throws IllegalMoveException {
        OthelloPosition pos = new OthelloPosition();
        pos.initialize();
        ArrayList<OthelloAction> list = pos.getMoves();
        assertEquals(list.size(), 4);
        pos = new OthelloPosition();
        assertEquals(pos.getMoves().size(), 0);
    }

    @Test
    public void makeSureDiskFlippingWorks() throws IllegalMoveException {
        OthelloPosition pos = new OthelloPosition();
        pos.initialize();
        OthelloAction action = new OthelloAction(6, 4);
        pos.makeMove(action);
        action = new OthelloAction(4, 3);
        pos.makeMove(action);
        action = new OthelloAction(3, 2);
        pos.makeMove(action);
        assertEquals(pos.board[4][3], 'W');
        pos.board[4][3] = 'B';
        action = new OthelloAction(6, 5);
        pos.makeMove(action);
        assertEquals(pos.board[5][4], 'B');
    }

    @Test
    public void naiveOthelloEvaluatorShouldReturnCorrect(){
        OthelloPosition pos = new OthelloPosition();
        pos.initialize();
        NaiveOthelloEvaluator evaluator = new NaiveOthelloEvaluator();
        assertEquals(evaluator.evaluate(pos), 0);
        pos.board[1][1] = 'W';
        assertEquals(evaluator.evaluate(pos), 1);
        pos.board[2][2] = 'B';
        assertEquals(evaluator.evaluate(pos), 0);
    }

    @Test
    public void canMakeMoveWorks() throws IllegalMoveException {
        OthelloPosition pos = new OthelloPosition();
        assertFalse(pos.canMakeMove());
        pos.initialize();
        assertTrue(pos.canMakeMove());
        pos.makeMove(pos.getMoves().get(0));
        pos.playerToMove = !pos.playerToMove;
        pos.makeMove(pos.getMoves().get(0));
        assertFalse(pos.canMakeMove());
        pos.playerToMove = !pos.playerToMove;
        assertFalse(pos.canMakeMove());
    }

    @Test
    public void gameIsPlayableWorks(){

    }



}
