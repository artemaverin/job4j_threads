package ru.job4j.pools;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    void whenConsistent() {
        int q = 0;
        int[][] arr = new int[3][3];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = q;
                q++;
            }
        }
        Sums check = new Sums();
        check.setRowSum(3);
        check.setColSum(9);
        assertThat(RolColSum.sum(arr)[0]).isEqualTo(check);
    }

    @Test
    void whenAsync() throws ExecutionException, InterruptedException {
        int q = 0;
        int[][] arr = new int[3][3];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = q;
                q++;
            }
        }
        Sums check = new Sums();
        check.setRowSum(12);
        check.setColSum(12);
        assertThat(RolColSum.asyncSum(arr)[1]).isEqualTo(check);
    }

    @Test
    void whenSumException() {
        int q = 0;
        int[][] arr = new int[2][3];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = q;
                q++;
            }
        }
        assertThatThrownBy(() -> RolColSum.sum(arr)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenAsyncSumException() {
        int q = 0;
        int[][] arr = new int[][]{{1, 2}};
        assertThatThrownBy(() -> RolColSum.asyncSum(arr)).isInstanceOf(IllegalArgumentException.class);
    }

}