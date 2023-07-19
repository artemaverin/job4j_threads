package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) {
        StringBuilder builder = new StringBuilder();
        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = stream.read()) != -1) {
                if (filter.test((char) data)) {
                    builder.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
