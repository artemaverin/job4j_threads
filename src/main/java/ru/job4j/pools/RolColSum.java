package ru.job4j.pools;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class RolColSum {

    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

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
