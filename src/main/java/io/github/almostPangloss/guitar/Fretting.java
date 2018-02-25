package io.github.almostPangloss.guitar;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Fretting implements Map<Wire, Integer> {
    private final Map<Wire, Integer> fretting;

    public Fretting(final Map<Wire, Integer> fretting) {
        this.fretting = new HashMap<>(fretting);
    }
    
    public Fretting() {
        this(new HashMap<>());
    }

    @Override
    public void clear() {
        fretting.clear();
    }

    @Override
    public boolean containsKey(final Object key) {
        return fretting.containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value) {
        return fretting.containsValue(value);
    }

    @Override
    public Set<Entry<Wire, Integer>> entrySet() {
        return fretting.entrySet();
    }

    @Override
    public Integer get(final Object key) {
        return fretting.get(key);
    }

    @Override
    public boolean isEmpty() {
        return fretting.isEmpty();
    }

    @Override
    public Set<Wire> keySet() {
        return fretting.keySet();
    }

    @Override
    public Integer put(final Wire key, final Integer value) {
        return fretting.put(key, value);
    }

    @Override
    public void putAll(final Map<? extends Wire, ? extends Integer> entries) {
        fretting.putAll(entries);
    }

    @Override
    public Integer remove(final Object key) {
        return fretting.remove(key);
    }

    @Override
    public int size() {
        return fretting.size();
    }

    @Override
    public Collection<Integer> values() {
        return fretting.values();
    }
}
