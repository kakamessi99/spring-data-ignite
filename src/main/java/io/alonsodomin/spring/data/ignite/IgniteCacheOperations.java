package io.alonsodomin.spring.data.ignite;

/**
 * Created by domingueza on 22/06/15.
 */
public interface IgniteCacheOperations<T, ID> {

    T getById(ID id);

}
