/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class

Control extends Thread {

    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 5000;

    private final int NDATA = MAXVALUE / NTHREADS;

    private PrimeFinderThread pft[];
    private static boolean paused = false;

    private static final Object lock = new Object();

    private static final AtomicInteger primeCount = new AtomicInteger(0);

    private Control() {
        super();
        this.pft = new PrimeFinderThread[NTHREADS];

        int i;
        for (i = 0; i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i * NDATA, (i + 1) * NDATA, lock);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i * NDATA, MAXVALUE + 1, lock);
    }

    public static Control newControl() {
        return new Control();
    }

    @Override
    public void run() {
        for (int i = 0; i < NTHREADS; i++) {
            pft[i].start();
        }

        while (true) {
            try {
                Thread.sleep(TMILISECONDS);

                pauseThreads();

                // Mostrar número total de primos encontrados
                int totalPrimos = 0;
                for (PrimeFinderThread thread : pft) {
                    totalPrimos += thread.getPrimes().size();
                }
                System.out.println("Número total de primos encontrados hasta ahora: " + totalPrimos);

                // Esperar la entrada del usuario para reanudar
                System.out.println("Presione ENTER para continuar...");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();

                resumeThreads();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void pauseThreads() {
        synchronized (lock) {
            paused = true;
            lock.notifyAll(); // Despierta a los hilos para que se puedan pausar
        }
    }

    private void resumeThreads() {
        synchronized (lock) {
            paused = false;
            lock.notifyAll(); // Reanudar todos los hilos
        }
    }

    public static boolean isPaused() {
        return paused;
    }
}
