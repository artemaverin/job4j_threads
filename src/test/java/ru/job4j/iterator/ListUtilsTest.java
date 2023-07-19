package ru.job4j.iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

class ListUtilsTest {
    private List<Integer> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>(Arrays.asList(1, 3));
    }

    @Test
    void whenAddBefore() {
        ListUtils.addBefore(input, 1, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddBeforeWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addBefore(input, 3, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenAddAfter() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    public void whenRemoveIf() {
        ListUtils.removeIf(input, e -> e > 1);
        assertThat(input).hasSize(1).contains(1);
    }

    @Test
    public void whenReplaceIf() {
        ListUtils.replaceIf(input, e -> e.equals(1), 2);
        assertThat(input).hasSize(2).containsSequence(2, 3);
    }

    @Test
    public void whenRemoveAll() {
        input.add(5);
        List<Integer> list = Arrays.asList(1, 5);
        ListUtils.removeAll(input, list);
        assertThat(input).hasSize(1).contains(3);
    }
}