package com.ddd.cat.infra;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FifoFixedSizedQueueTest {
    @Test
    void shouldRemoveFirstItemWhenAddingItemAboveMaxSize() {
        FifoFixedSizedQueue<Integer> queue = new FifoFixedSizedQueue<>(2);
        Integer firstItem = 1;
        Integer secondItem = 2;
        Integer thirdItem = 3;
        queue.add(firstItem);
        queue.add(secondItem);
        assertEquals(2, queue.size());
        assertEquals(firstItem, queue.peek());
        queue.add(thirdItem);
        assertEquals(secondItem, queue.peek());
        assertTrue(queue.contains(thirdItem));
    }
}