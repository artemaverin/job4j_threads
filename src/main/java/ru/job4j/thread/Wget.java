package ru.job4j.thread;

import java.io.*;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String name;

    public Wget(String url, int speed, String name) {
        this.url = url;
        this.speed = speed;
        this.name = name;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(name + ".xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long time = System.currentTimeMillis();
            long difference = 0;
            int totalReadSpeed = 0;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                if (totalReadSpeed >= speed) {
                    totalReadSpeed += bytesRead;
                }
                difference = System.currentTimeMillis() - time;
                if (difference < 1000) {
                    Thread.sleep(1000 - difference);
                }
                time = System.currentTimeMillis();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void isValid(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("not enough parameters");
        }
        if (!(args[0].endsWith(".xml") || args[0].endsWith(".html") || args[0].endsWith(".txt"))) {
            throw new IllegalArgumentException("format is incorrect");
        }
        if (Integer.parseInt(args[1]) < 0) {
            throw new IllegalArgumentException("speed must be more than zero");
        }
        if (" ".equals(args[2]) || "".equals(args[2])) {
            throw new IllegalArgumentException("file name must contain at least one symbol");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        isValid(args);
        String url = args[0];
        String name = args[2];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed, name));
        wget.start();
        wget.join();

    }
}
