package ru.job4j.collection;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimpleSetTest {

    @Test
    void whenAddNonNull() {
        Set<Integer> set = new SimpleSet<>();
        assertThat(set.add(1)).isTrue();
        assertThat(set.contains(1)).isTrue();
        assertThat(set.add(1)).isFalse();
    }

    @Test
    void whenAddNull() {
        Set<Integer> set = new SimpleSet<>();
        assertThat(set.add(null)).isTrue();
        assertThat(set.contains(null)).isTrue();
        assertThat(set.add(null)).isFalse();
    }

    @Test
    void checkIterator() {
        Set<Integer> set = new SimpleSet<>();
        set.add(1);
        set.add(2);
        set.add(4);
        assertThat(set).hasSize(3);
    }

    @Test
    void whenGetIteratorFromEmptySetThenHasNextReturnFalse() {
        Set<Integer> set = new SimpleSet<>();
        set.add(1);
        assertThat(set.iterator().hasNext()).isTrue();
        assertThat(set.iterator().next()).isEqualTo(1);
    }

    @Test
    void whenGetIteratorFromEmptySetThenNextThrowException() {
        Set<Integer> set = new SimpleSet<>();
        assertThatThrownBy(set.iterator()::next)
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void whenGetIteratorTwiceThenStartAlwaysFromBeginning() {
        Set<Integer> set = new SimpleSet<>();
        set.add(1);
        assertThat(set.iterator().next()).isEqualTo(1);
        assertThat(set.iterator().next()).isEqualTo(1);
    }

    @Test
    void whenCheckIterator() {
        Set<Integer> set = new SimpleSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        Iterator<Integer> iterator = set.iterator();
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(2);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(3);
        assertThat(iterator.hasNext()).isFalse();
    }
}