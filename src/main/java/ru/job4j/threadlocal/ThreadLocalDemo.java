package ru.job4j.threadlocal;

public class ThreadLocalDemo {
    public static ThreadLocal<String> tl = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        Thread one = new FirstThread();
        Thread two = new SecondThread();
        tl.set("main поток");
        System.out.println(tl.get());
        one.start();
        two.start();
        one.join();
        two.join();
    }
}
