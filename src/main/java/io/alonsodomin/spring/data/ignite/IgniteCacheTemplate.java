package io.alonsodomin.spring.data.ignite;

import org.apache.ignite.IgniteCache;
import org.springframework.util.Assert;

import java.io.Serializable;

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
    public long count() {
        return cache.size();
    }

    @Override
    public void clear() {
        cache.clear();
    }
}
