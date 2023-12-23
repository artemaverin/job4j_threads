package ru.job4j.pool;

import ru.job4j.wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    int size = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }));
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
        threads.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        Runnable runnable = () -> System.out.println(" Hello " + Thread.currentThread().getName());
        Runnable runnable2 = () -> System.out.println(" Hello 2 " + Thread.currentThread().getName());
        pool.work(runnable);
        Thread.sleep(10);
        pool.work(runnable2);
        Thread.sleep(2000);
        pool.shutdown();

    }

}
