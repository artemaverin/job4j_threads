package ru.job4j.pools;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MyParallelSearchTest {


    @Test
    void whenIntegerType() {
        Integer[] array = new Integer[50];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        assertThat(MyParallelSearch.start(array, 25)).isEqualTo(24);
    }

    @Test
    void whenStringType() {
        String[] array = new String[50];
        for (int i = 0; i < array.length; i++) {
            array[i] = String.valueOf(i + 1);
        }
        assertThat(MyParallelSearch.start(array, "25")).isEqualTo(24);
    }

    @Test
    void whenLineaSearch() {
        Integer[] array = new Integer[9];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        assertThat(MyParallelSearch.start(array, 4)).isEqualTo(3);
    }

    @Test
    void whenParallelSearch() {
        Integer[] array = new Integer[10000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        assertThat(MyParallelSearch.start(array, 9999)).isEqualTo(9998);
    }

    @Test
    void whenObjectNotFound() {
        Integer[] array = new Integer[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        assertThat(MyParallelSearch.start(array, 666)).isEqualTo(-1);
    }

}