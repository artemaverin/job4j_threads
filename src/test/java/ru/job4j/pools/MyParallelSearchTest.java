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
        MyParallelSearch<Integer> search = new MyParallelSearch<>(25, array);
        assertThat(search.start()).isEqualTo(24);
    }

    @Test
    void whenStringType() {
        String[] array = new String[50];
        for (int i = 0; i < array.length; i++) {
            array[i] = String.valueOf(i + 1);
        }
        MyParallelSearch<String> search = new MyParallelSearch<>("25", array);
        assertThat(search.start()).isEqualTo(24);
    }

    @Test
    void whenLineaSearch() {
        Integer[] array = new Integer[9];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        MyParallelSearch<Integer> search = new MyParallelSearch<>(4, array);
        assertThat(search.start()).isEqualTo(3);
    }

    @Test
    void whenParallelSearch() {
        Integer[] array = new Integer[10000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        MyParallelSearch<Integer> search = new MyParallelSearch<>(9999, array);
        assertThat(search.start()).isEqualTo(9998);
    }

    @Test
    void whenObjectNotFound() {
        Integer[] array = new Integer[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        MyParallelSearch<Integer> search = new MyParallelSearch<>(666, array);
        assertThat(search.start()).isEqualTo(-1);
    }

}