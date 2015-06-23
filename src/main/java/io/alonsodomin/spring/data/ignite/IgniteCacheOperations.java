package io.alonsodomin.spring.data.ignite;

import java.util.Map;

/**
 * Created by domingueza on 22/06/15.
 */
public interface IgniteCacheOperations<T, ID> {

    T getById(ID id);

    boolean exists(ID id);

    long count();

    void clear();

    T save(ID id, T newValue);

    void save(Map<ID, T> values);

}
