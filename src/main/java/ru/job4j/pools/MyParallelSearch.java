package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MyParallelSearch<T> extends RecursiveTask<Integer> {

    private final T object;
    private final T[] array;
    private final int from;
    private final int to;

    public MyParallelSearch(T object, T[] array, int from, int to) {
        this.object = object;
        this.array = array;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if ((to - from) <= 10) {
            return search();
        }
        int middle = (from + to) / 2;

        MyParallelSearch<T> first = new MyParallelSearch<>(object, array, from, middle + 1);
        MyParallelSearch<T> second = new MyParallelSearch<>(object, array, middle + 1, to);

        first.fork();
        second.fork();

        Integer firstJoin = first.join();
        Integer secondJoin = second.join();

        return Math.max(firstJoin, secondJoin);
    }

    public Integer search() {
        int res = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(object)) {
                res = i;
                break;
            }
        }
        return res;
    }

    public static <T> Integer start(T[] array, T object) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(
                new MyParallelSearch<>(object, array, 0, array.length - 1));
    }
}
