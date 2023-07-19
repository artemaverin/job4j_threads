package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ForwardLinked<T> implements Iterable<T> {

    public static void main(String[] args) {
        ForwardLinked<Integer> list = new ForwardLinked<Integer>();
        list.add(2);
        list.add(4);
        list.add(8);

        System.out.println(list);
        System.out.println("===================");

        ForwardLinked<Integer> list2 = new ForwardLinked<Integer>();
        list2.addFirst(2);
        list2.addFirst(4);
        list2.addFirst(8);

        System.out.println(list2);
    }

    @Override
    public String toString() {
        return "ForwardLinked{" +
                "size=" + size +
                ", modCount=" + modCount +
                ", head=" + head +
                '}';
    }


    private int size = 0;
    private int modCount = 0;
    private Node<T> head;

    public void add(T value) {
        Node<T> newNode = new Node<T>(value, null);
        Node<T> node = head;
        if (head == null) {
            head = newNode;
        } else {
            while (node.next != null) {
                node = node.next;
            }
            node.next = newNode;
        }
        size++;
        modCount++;
    }

    public void addFirst(T value) {
        Node<T> node = head;
        Node<T> newNode = new Node<>(value, node);
        head = newNode;
    }

    public T get(int index) {
        Objects.checkIndex(index, size);
        Node<T> x = head;
        int count = 0;
        while (x != null) {
            if (count == index) {
                return x.item;
            }
            x = x.next;
            count++;
        }
        throw new IndexOutOfBoundsException();
    }

    public T deleteFirst() {
        if (head != null) {
            Node<T> node = head;
            T del = node.item;
            head = node.next;
            node.next = null;
            node.item = null;
            return del;
        }
        throw new NoSuchElementException();

    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> iterNode = head;
            final int expectedModCount = modCount;
            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return iterNode != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Node<T> node = iterNode;
                iterNode = iterNode.next;
                return node.item;
            }
        };
    }

    private static class Node<T> {
        T item;
        Node<T> next;

        public Node(T item, Node<T> next) {
            this.item = item;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "item=" + item +
                    ", next=" + next +
                    '}';
        }
    }
}
