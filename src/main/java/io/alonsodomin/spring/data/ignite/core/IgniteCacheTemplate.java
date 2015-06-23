package io.alonsodomin.spring.data.ignite.core;

import com.google.common.collect.Sets;
import org.apache.ignite.IgniteCache;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by domingueza on 22/06/15.
 */
public class IgniteCacheTemplate<T, ID extends Serializable> implements IgniteCacheOperations<T, ID> {

    private final IgniteCache<ID, T> cache;

    public IgniteCacheTemplate(IgniteCache<ID, T> cache) {
        Assert.notNull(cache);
        this.cache = cache;
    }

    @Override
    public T getById(ID id) {
        return cache.get(id);
    }

    @Override
    public boolean exists(ID id) {
        return cache.containsKey(id);
    }

    @Override
    public Iterable<T> fetchAll() {
        List<T> itemList = new ArrayList<>();
        cache.forEach(entry -> itemList.add(entry.getValue()) );
        return itemList;
    }

    @Override
    public Iterable<T> fetch(Iterable<? extends ID> ids) {
        Set<? extends ID> idSet = Sets.newHashSet(ids);
        Map<? extends ID, T> entryMap = cache.getAll(idSet);
        return entryMap.values();
    }

    @Override
    public long count() {
        return cache.size();
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public T remove(ID id) {
        return cache.getAndRemove(id);
    }

    @Override
    public void remove(Set<? extends ID> ids) {
        cache.removeAll(ids);
    }

    @Override
    public T save(ID id, T newValue) {
        return cache.getAndPut(id, newValue);
    }

    @Override
    public void save(Map<? extends ID, ? extends T> values) {
        cache.putAll(values);
    }
}
