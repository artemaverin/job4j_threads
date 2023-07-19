package ru.job4j.map;

import java.lang.reflect.Array;
import java.util.*;

public class SimpleMap<K, V> implements Map<K, V> {

    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;

    private int count = 0;

    private int modCount = 0;

    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        boolean res = false;
        if ((float) count / (float) capacity >= LOAD_FACTOR) {
            expand();
        }
        MapEntry<K, V> map = new MapEntry<>(key, value);
        int index = indexFor(hash(Objects.hashCode(key)));
        if (table[index] == null) {
            table[index] = map;
            count++;
            modCount++;
            res = true;
        }

        return res;
    }

    private int hash(int hashCode) {
        return hashCode == 0 ? 0 : hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return hash & (table.length - 1);
    }

    private void expand() {
        capacity *= 2;
        MapEntry<K, V>[] newTable = new MapEntry[capacity];
        for (MapEntry<K, V> entry:table) {
            if (entry != null) {
                int index = indexFor(hash(Objects.hashCode(entry.key)));
                newTable[index] = entry;
            }

        }
        table = newTable;
    }

    @Override
    public V get(K key) {
        V res = null;
        for (MapEntry<K, V> mapEntry : table) {
            if (mapEntry != null
                    && hash(Objects.hashCode(key)) == hash(Objects.hashCode(mapEntry.key))
                    && Objects.equals(mapEntry.key, key)) {
                res = mapEntry.value;
                break;
            }
        }
        return res;
    }

    @Override
    public boolean remove(K key) {
        boolean res = false;
        int index = indexFor(hash(Objects.hashCode(key)));
        if (table[index] != null
                && hash(Objects.hashCode(key)) == hash(Objects.hashCode(table[index].key))
                && Objects.equals(table[indexFor(hash(Objects.hashCode(key)))].key, key)) {
            table[index] = null;
            res = true;
            count--;
            modCount++;
        }
        return res;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private int expectedModCount = modCount;
            private int s = 0;
            private int k = 0;
            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return s < count;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                for (int i = k; i < table.length; i++) {
                    if (table[i] != null) {
                        k = i;
                        break;
                    }
//                    i++;
                }
                s++;
                return table[k++].key;
            }
        };
    }

    private static class MapEntry<K, V> {

        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
