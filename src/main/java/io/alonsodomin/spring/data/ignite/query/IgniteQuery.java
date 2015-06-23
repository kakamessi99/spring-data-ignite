package io.alonsodomin.spring.data.ignite.query;

import io.alonsodomin.spring.data.ignite.core.IgniteCacheOperations;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.repository.query.RepositoryQuery;

import java.io.Serializable;

/**
 * Created by aalonsodominguez on 23/06/15.
 */
public class IgniteQuery<T, ID extends Serializable> implements RepositoryQuery {

    private final IgniteCacheOperations<T, ID> igniteCacheOperations;
    private final IgniteQueryMethod<T, ID> queryMethod;

    public IgniteQuery(IgniteCacheOperations<T, ID> igniteCacheOperations, IgniteQueryMethod<T, ID> queryMethod) {
        this.igniteCacheOperations = igniteCacheOperations;
        this.queryMethod = queryMethod;
    }

    @Override
    public Object execute(Object[] parameters) {
        return null;
    }

    @Override
    public QueryMethod getQueryMethod() {
        return queryMethod;
    }
}
