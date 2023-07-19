package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleLinkedList<E> implements LinkedList<E> {

    public static void main(String[] args) {
        SimpleLinkedList<Integer> list = new SimpleLinkedList<Integer>();
        list.add(2);
        list.add(4);
        list.add(8);
        System.out.println(list);
    }

    @Override
    public String toString() {
        return "SimpleLinkedList{" +
                "size=" + size +
                ", modCount=" + modCount +
                ", head=" + head +
                '}';
    }

    private int size = 0;
    private int modCount = 0;
    private Node<E> head;

    @Override
    public void add(E value) {
        Node<E> newNode = new Node<E>(value, null);
        Node<E> node = head;
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

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        Node<E> x = head;
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

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> iterNode = head;
            final int expectedModCount = modCount;
            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return iterNode != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Node<E> node = iterNode;
                iterNode = iterNode.next;
                return node.item;
            }
        };
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;

        Node(E element, Node<E> next) {
            this.item = element;
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
