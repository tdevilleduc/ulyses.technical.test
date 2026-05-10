package com.septeo.ulyses.technical.test.util;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class CustomCache<K, V> {
    private final AtomicReference<Map<K, V>> snapshot = new AtomicReference<>(Map.of());
    private final Supplier<Map<K, V>> loader;
    private volatile Instant loadedAt = Instant.EPOCH;
    private final Duration ttl;

    public CustomCache(Supplier<Map<K, V>> loader, Duration ttl) {
        this.loader = loader;
        this.ttl = ttl;
    }

    public V get(K key) {
        return current().get(key);
    }

    public List<V> findAll() {
        return current().values().stream()
            .toList();
    }

    private Map<K, V> current() {
        if (Instant.now().isAfter(loadedAt.plus(ttl))) {
            reload();
        }
        return snapshot.get();
    }

    public V refreshNowWith(Supplier<V> dbOperation) {
        synchronized (this) {
        V result = dbOperation.get();
            snapshot.set(Map.copyOf(loader.get()));
            loadedAt = Instant.now();
                    return result;

        }
    }

    public void evictWith(K key, Runnable dbOperation) {
        synchronized (this) {
            dbOperation.run();
            Map<K, V> updated = new HashMap<>(snapshot.get());
            updated.remove(key);
            snapshot.set(Map.copyOf(updated));
        }
    }

    private synchronized void reload() {
        if (Instant.now().isBefore(loadedAt.plus(ttl))) return;
        snapshot.set(Map.copyOf(loader.get()));
        loadedAt = Instant.now();
    }
    
    public void reset() {
        synchronized (this) {
            snapshot.set(Map.of());
            loadedAt = Instant.EPOCH;
    }
}
}
