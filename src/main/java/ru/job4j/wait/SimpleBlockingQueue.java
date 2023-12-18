package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    private final Object monitor = this;

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    private final int size;

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public synchronized void offer(T value) throws InterruptedException {
        if (queue.size() == size) {
            monitor.wait();
        }
        queue.add(value);
        monitor.notifyAll();
    }


    public synchronized T poll() throws InterruptedException {
        if (queue.size() == 0) {
            monitor.wait();
        }
        T value = queue.poll();
        monitor.notifyAll();
        return value;
    }

    public synchronized int getSize() {
        return queue.size();
    }
}
