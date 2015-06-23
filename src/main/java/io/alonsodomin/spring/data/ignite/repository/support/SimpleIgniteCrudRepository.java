package io.alonsodomin.spring.data.ignite.repository.support;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import io.alonsodomin.spring.data.ignite.IgniteCacheOperations;
import io.alonsodomin.spring.data.ignite.repository.IgniteCrudRepository;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toMap;

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
        ID entityId = entityInformation.getId(entity);
        igniteCacheOperations.save(entityId, entity);
        return entity;
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        Map<ID, S> entityMap = StreamSupport.stream(entities.spliterator(), false)
                .collect(toMap(entityInformation::getId, Function.identity()));
        igniteCacheOperations.save(entityMap);
        return entities;
    }

    @Override
    public T findOne(ID id) {
        return igniteCacheOperations.getById(id);
    }

    @Override
    public boolean exists(ID id) {
        return igniteCacheOperations.exists(id);
    }

    @Override
    public Iterable<T> findAll() {
        return igniteCacheOperations.fetchAll();
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        return igniteCacheOperations.fetch(ids);
    }

    @Override
    public long count() {
        return igniteCacheOperations.count();
    }

    @Override
    public void delete(ID id) {
        igniteCacheOperations.remove(id);
    }

    @Override
    public void delete(T entity) {
        ID id = entityInformation.getId(entity);
        igniteCacheOperations.remove(id);
    }

    @Override
    public void delete(Iterable<? extends T> entities) {
        Set<ID> idsToDelete = Sets.newHashSet(Iterables.transform(entities, entityInformation::getId));
        igniteCacheOperations.remove(idsToDelete);
    }

    @Override
    public void deleteAll() {
        igniteCacheOperations.clear();
    }
}
