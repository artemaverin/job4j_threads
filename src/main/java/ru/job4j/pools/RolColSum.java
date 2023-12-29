package ru.job4j.pools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class RolColSum {

    private static final Logger LOGGER = Logger.getGlobal();

    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        if ((n != matrix[0].length) || (n == 1)) {
            throw new IllegalArgumentException("матрица не квадратная");
        }
        Sums[] array = new Sums[n];
        for (int i = 0; i < n; i++) {
            Sums sums = new Sums();
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < n; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums.setRowSum(rowSum);
            sums.setColSum(colSum);
            array[i] = sums;
        }

        return array;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        if ((n != matrix[0].length) || (n == 1)) {
            throw new IllegalArgumentException("матрица не квадратная");
        }
        Sums[] array = new Sums[n];
        int k = 0;
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < n; i++) {
            futures.put(k, getTask(matrix, i));
            k++;
        }
        for (Integer key : futures.keySet()) {
            array[key] = futures.get(key).get();
        }

        return array;
    }

    public static CompletableFuture<Sums> getTask(int[][] data, int start) {
        return CompletableFuture.supplyAsync(() -> {
            int sumRow = 0;
            int sumCol = 0;
            Sums sums = new Sums();
            for (int i = 0; i < data.length; i++) {
                sumRow += data[start][i];
                sumCol += data[i][start];
            }
            LOGGER.info(Thread.currentThread().getName());
            sums.setRowSum(sumRow);
            sums.setColSum(sumCol);
            return sums;
        });
    }
}
