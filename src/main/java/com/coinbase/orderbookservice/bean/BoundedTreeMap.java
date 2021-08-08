package com.coinbase.orderbookservice.bean;

import java.util.Collections;
import java.util.Set;
import java.util.TreeMap;

public class BoundedTreeMap<K extends Comparable<K>, V> {
    private TreeMap<K,V> map;
    private final int size;

    public BoundedTreeMap(int size) {
       this(size, false);
    }

    public BoundedTreeMap(int size, boolean reverseOrder) {
        this.size = size;
        this.map = reverseOrder ? new TreeMap<>(Collections.reverseOrder()): new TreeMap<>();
    }

    public boolean put(K key, V val) {
        boolean sameKeyDiscarded = false;
        V oldValue = map.put(key, val);
        if(map.size() > size) {
            K lastKey = map.lastKey();
            map.remove(lastKey);
            sameKeyDiscarded = lastKey.equals(key);
        }
        return !sameKeyDiscarded && !val.equals(oldValue);

    }

    public boolean remove(K key) {
       return map.remove(key) != null;

    }

    public V get(K key) {
        return map.get(key);
    }

    public int size() {
        return map.size();
    }

    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        map.entrySet().forEach(e -> sb.append("[" +  e.getKey() + ", " + e.getValue() + "], "  ));
        sb.append("]");
        return sb.toString();
    }
}
