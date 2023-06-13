package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }

    @Override
    public void run() {
        int i = 0;
        var process = new char[] {'-', '\\', '|', '/'};
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r load: " + process[i]);
            i++;
            i = i % process.length;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
