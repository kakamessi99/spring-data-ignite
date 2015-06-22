package io.alonsodomin.spring.data.ignite.repository.support;

import io.alonsodomin.spring.data.ignite.IgniteCacheOperations;
import io.alonsodomin.spring.data.ignite.repository.IgniteCrudRepository;

import java.io.Serializable;

/**
 * Created by domingueza on 22/06/15.
 */
public class SimpleIgniteCrudRepository<T, ID extends Serializable> implements IgniteCrudRepository<T, ID> {

    private final IgniteEntityInformation<T, ID> entityInformation;
    private final IgniteCacheOperations<T, ID> igniteCacheOperations;

    public SimpleIgniteCrudRepository(IgniteEntityInformation<T, ID> entityInformation, IgniteCacheOperations<T, ID> igniteCacheOperations) {
        this.entityInformation = entityInformation;
        this.igniteCacheOperations = igniteCacheOperations;
    }

    @Override
    public <S extends T> S save(S entity) {
        return null;
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        return null;
    }

    @Override
    public T findOne(ID id) {
        return igniteCacheOperations.getById(id);
    }

    @Override
    public boolean exists(ID id) {
        return false;
    }

    @Override
    public Iterable<T> findAll() {
        return null;
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(ID id) {

    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public void delete(Iterable<? extends T> entities) {

    }

    @Override
    public void deleteAll() {
        igniteCacheOperations.clear();
    }
}
