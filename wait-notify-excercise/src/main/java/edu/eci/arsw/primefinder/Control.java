/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Control extends Thread {

    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 3000;
    private final static int TMILISECONDS = 500;

    private final int NDATA = MAXVALUE / NTHREADS;
    private final Object monitor = new Object();
    private PrimeFinderThread[] pft;
    private BufferedReader reader;

    private Control() {
        super();
        this.pft = new PrimeFinderThread[NTHREADS];
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    
        for (int i = 0; i < NTHREADS - 1; i++) {
            int start = i * NDATA;
            int end = (i + 1) * NDATA;
            PrimeFinderThread elem = new PrimeFinderThread(start, end, monitor);
            pft[i] = elem;
        }
        int start = (NTHREADS - 1) * NDATA;
        int end = MAXVALUE + 1;
        pft[NTHREADS - 1] = new PrimeFinderThread(start, end, monitor);
    }
    

    public static Control newControl() {
        return new Control();
    }

    @Override
    public void run() {
        for (PrimeFinderThread thread : pft) {
            thread.start();
        }

        while (true) {
            try {
                Thread.sleep(TMILISECONDS);
                synchronized (monitor) {
                    for (PrimeFinderThread thread : pft) {
                        thread.pauseThread();  // Pausa los hilos
                    }
                    System.out.println("Número de primos encontrados: " + getTotalPrimesFound());
                    System.out.println("Presiona ENTER para continuar...");
                    reader.readLine(); // Lee una línea completa, esperando a que el usuario presione ENTER

                    for (PrimeFinderThread thread : pft) {
                        thread.resumeThread();  // Reanuda los hilos
                    }
                    monitor.notifyAll();  // Notifica a todos los hilos
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getTotalPrimesFound() {
        int total = 0;
        for (PrimeFinderThread thread : pft) {
            total += thread.getPrimesCount();
        }
        return total;
    }

    public PrimeFinderThread[] getPrimeFinderThreads() {
        return pft;
    }

    public Object getMonitor() {
        return monitor;
    }

    public void pauseThreads() {
        synchronized (monitor) {
            for (PrimeFinderThread thread : pft) {
                thread.pauseThread();
            }
        }
    }

    public void resumeThreads() {
        synchronized (monitor) {
            for (PrimeFinderThread thread : pft) {
                thread.resumeThread();
            }
            monitor.notifyAll();
        }
    }



}