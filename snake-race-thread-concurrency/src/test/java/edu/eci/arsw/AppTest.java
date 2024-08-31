package edu.eci.arsw;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import snakepackage.Board;
import snakepackage.Cell;
import snakepackage.Snake;
import enums.Direction;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private Cell cell;
    private Snake snake;
    private Cell head;

    @BeforeEach
    public void setUp() {
        // Initialize Cell and Snake objects for testing
        cell = new Cell(5, 5);
        head = new Cell(5, 5);
        snake = new Snake(1, head, Direction.RIGHT);
    }

    // Tests for Cell class
    @Test
    public void testCellInitialValues() {
        assertEquals(5, cell.getX());
        assertEquals(5, cell.getY());
        assertFalse(cell.isFull());
        assertFalse(cell.isFood());
        assertFalse(cell.isJump_pad());
        assertFalse(cell.isTurbo_boost());
        assertFalse(cell.isBarrier());
    }

    @Test
    public void testCellHasElements() {
        assertFalse(cell.hasElements());
        cell.setFood(true);
        assertTrue(cell.hasElements());
        cell.setFood(false);
        cell.setFull(true);
        assertTrue(cell.hasElements());
        cell.setFull(false);
        cell.setJump_pad(true);
        assertTrue(cell.hasElements());
    }

    @Test
    public void testApp() {
        // Example test to ensure the test framework is working
        assertTrue(true);
    }
}
