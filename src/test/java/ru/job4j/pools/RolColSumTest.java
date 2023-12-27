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
        assertThat(RolColSum.sum(arr)[2].getRowSum()).isEqualTo(21);
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
        assertThat(RolColSum.asyncSum(arr)[1].getRowSum()).isEqualTo(12);
    }

}