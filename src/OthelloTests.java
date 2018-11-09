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
        assertTrue(pos.inside_bounds(0, 0));
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

}
