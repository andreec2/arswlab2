package edu.eci.arsw.primefinder;

import org.junit.Test;

import static org.junit.Assert.*;

public class ControlTest {

    @Test
    public void testGetTotalPrimesFound() {
        Control control = Control.newControl();
        control.start();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int totalPrimes = control.getTotalPrimesFound();
        assertEquals(430, totalPrimes);
    }

    @Test
    public void testTotalPrimesFound() throws InterruptedException {
        Control control = Control.newControl();
        control.start();
        Thread.sleep(6000);
        int totalPrimes = control.getTotalPrimesFound();
        assertTrue(totalPrimes >= 0);
        assertFalse(totalPrimes <= 0);
    }

    @Test
    public void testThreadRanges() {
        Control control = Control.newControl();
        control.start();
        int max = 3000;
        int numThreads = 3;
        int range = max / numThreads;
        PrimeFinderThread[] threads = control.getPrimeFinderThreads();

        for (int i = 0; i < numThreads; i++) {
            PrimeFinderThread thread = threads[i];
            int start = i * range;
            int end = (i == numThreads - 1) ? max + 1 : (i + 1) * range;
            assertEquals(i, start, thread.getStart());
            assertEquals(i, end, thread.getEnd());
        }

    }

}
