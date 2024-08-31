package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread {

    private int a, b;
    private List<Integer> primes;
    private final Object monitor;
    private boolean paused;

    public PrimeFinderThread(int a, int b, Object monitor) {
        super();
        this.primes = new LinkedList<>();
        this.a = a;
        this.b = b;
        this.monitor = monitor;
        this.paused = false;
    }

    @Override
    public void run() {
        for (int i = a; i < b; i++) {
            synchronized (monitor) {
                while (paused) {
                    try {
                        monitor.wait(); // Espera a ser notificado para reanudar
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (isPrime(i)) {
                primes.add(i);
                // System.out.println(i);
            }

        }
    }

    boolean isPrime(int n) {
        if (n <= 2) {
            return n == 2;
        }
        if (n % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public List<Integer> getPrimes() {
        return primes;
    }

    public int getPrimesCount() {
        return primes.size();
    }

    public void pauseThread() {
        paused = true;
    }

    public void resumeThread() {
        synchronized (monitor) {
            paused = false;
            monitor.notifyAll(); // Notifica a todos los hilos esperando en monitor
        }
    }

    public int getStart() {
        return a;
    }

    public int getEnd() {
        return b;
    }
}