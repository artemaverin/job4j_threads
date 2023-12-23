package ru.job4j.mail;

import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    ExecutorService pool;

    public EmailNotification() {
        this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void emailTo(User user) {
        if (user == null) {
            throw new NoSuchElementException();
        }
        String subject = String
                .format("subject = Notification {%s} to email {%s}.", user.getUsername(), user.getEmail());
        String body = String
                .format("body = Add a new event to {%s}", user.getUsername());

        pool.submit(() -> send(subject, body, user.getEmail()));
    }

    public void close() {
        pool.shutdown();
    }

    public void send(String subject, String body, String email) {
        System.out.println("Some code");
    }

    public static void main(String[] args) {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.emailTo(new User("Pavel", "pavel@mail.com"));
        emailNotification.close();
    }
}
