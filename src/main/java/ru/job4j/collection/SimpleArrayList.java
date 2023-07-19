package ru.job4j.collection;

import java.util.*;

public class SimpleArrayList<T> implements SimpleList<T> {

    private T[] container;
    private int size;
    private int modCount;

    public SimpleArrayList(int capacity) {
        container = (T[]) new Object[capacity];
    }

    private void expand() {
        if (container.length == 0) {
            container = Arrays.copyOf(container, 10);
        }
        container = Arrays.copyOf(container, container.length * 2);
    }

    @Override
    public void add(T value) {
        if (size == container.length) {
            expand();
        }
        container[size] = value;
        size++;
        modCount++;
    }


    @Override
    public T set(int index, T newValue) {
        Objects.checkIndex(index, size);
        T value = container[index];
        for (int i = 0; i < container.length; i++) {
            if (i == index) {
                container[i] = newValue;
                break;
            }
        }
        return value;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);
        T value = container[index];
        System.arraycopy(
                container, // откуда копируем
                index + 1, // начиная с какого индекса
                container, // куда копируем
                index, // начиная с какого индекса
                container.length - index - 1);
        container[container.length - 1] = null;
        size--;
        modCount++;
        return value;
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return container[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int count = 0;
            int expectedModCount = modCount;
            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return count < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return container[count++];
            }
        };
    }
}
