package com.youssef.GridPulse.seeder.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class ResourcePool<T> {
    private final Queue<T> pool = new LinkedList<>();

    public void addAll(Collection<T> items) {
        pool.addAll(items);
    }

    public synchronized T getOne() {
        T item = pool.poll();
        if (item == null) {
            throw new IllegalStateException("No more items available in resource pool");
        }
        return item;
    }
}
