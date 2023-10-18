package ru.otus.cachehw;


import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {


    private static final String ACTION_PUT = "PUT";
    private static final String ACTION_GET = "GET";
    private static final String ACTION_REMOVE = "REMOVE";
    private final Map<K, V> cacheMap = new WeakHashMap<>();
    private final Set<HwListener<K,V>> listenersSet = new HashSet<>();

    @Override
    public void put(K key, V value) {
        cacheMap.put(key, value);
        notifyListeners(key, value, ACTION_PUT);
    }

    @Override
    public void remove(K key) {
        cacheMap.remove(key);
        notifyListeners(key, null, ACTION_REMOVE);
    }

    @Override
    public V get(K key) {
        V result = cacheMap.get(key);
        notifyListeners(key, result, ACTION_GET);
        return result;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listenersSet.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listenersSet.remove(listener);
    }
    private void notifyListeners(K key, V value, String cacheListenerAction) {
        listenersSet.forEach(listener -> listener.notify(key, value, cacheListenerAction));
    }

}
