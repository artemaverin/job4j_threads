package ru.job4j.wait;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public synchronized void count() {
        count++;
        monitor.notifyAll();
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        CountBarrier barrier = new CountBarrier(5);


        Thread thread1 = new Thread(
                () -> {
                    System.out.println("запущен поток " + Thread.currentThread().getName());
                    barrier.await();
                    System.out.println("барьер пройден потоком " + Thread.currentThread().getName());
                }
        );


        Thread thread2 = new Thread(
                () -> {
                    System.out.println("запущен поток " + Thread.currentThread().getName());
                    barrier.await();
                    System.out.println("барьер пройден потоком " + Thread.currentThread().getName());
                }

        );

        thread1.start();
        thread2.start();

        for (int i = 0; i < 5; i++) {
            barrier.count();
        }
    }
}
