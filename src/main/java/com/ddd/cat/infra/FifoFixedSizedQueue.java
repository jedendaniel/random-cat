package com.ddd.cat.infra;

import java.util.AbstractQueue;
import java.util.Iterator;

public class FifoFixedSizedQueue<E> extends AbstractQueue<E> {
    private final int maxSize;
    private int currentSize;
    Object[] items;

    public FifoFixedSizedQueue(int maxSize) {
        this.maxSize = maxSize;
        this.items = new Object[maxSize];
    }

    @Override
    public Iterator<E> iterator() {
        return new Iter<>(this);
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException("Null not allowed in the queue");
        }
        if (currentSize == maxSize) {
            this.poll();

        }
        items[currentSize] = e;
        currentSize++;
        return true;
    }

    @Override
    public E poll() {
        if (currentSize == 0) {
            return null;
        }
        E e = (E) items[0];
        shiftLeft();
        currentSize--;
        return e;
    }

    private void shiftLeft() {
        for(int i = 0; i < items.length - 1; i++) {
            if (items[i] == null) {
                break;
            }
            items[i] = items[i+1];
        }
    }

    @Override
    public E peek() {
        if (currentSize <= 0) {
            return null;
        }
        return (E) items[0];
    }

    private static class Iter<E> implements Iterator<E> {
        private int index = 0;
        private FifoFixedSizedQueue<E> queue;

        public Iter(FifoFixedSizedQueue<E> queue) {
            this.queue = queue;
        }

        @Override
        public boolean hasNext() {
            return index < queue.size();
        }

        @Override
        public E next() {
            E item = (E) queue.items[index];
            index++;
            return item;
        }
    }
}
