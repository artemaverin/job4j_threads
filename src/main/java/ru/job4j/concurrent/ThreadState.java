package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        int i = 0;
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println("first thread = " + first.getState());
            System.out.println("second thread = " + second.getState());
        }
        System.out.println("работа завершена");
    }
}
