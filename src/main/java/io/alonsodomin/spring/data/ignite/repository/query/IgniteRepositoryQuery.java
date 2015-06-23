package io.alonsodomin.spring.data.ignite.repository.query;

import io.alonsodomin.spring.data.ignite.core.IgniteCacheOperations;
import org.springframework.data.repository.query.ParametersParameterAccessor;
import org.springframework.data.repository.query.RepositoryQuery;

import java.io.Serializable;

/**
 * Created by aalonsodominguez on 23/06/15.
 */
public class IgniteRepositoryQuery<T, ID extends Serializable> implements RepositoryQuery {

    private final IgniteCacheOperations<T, ID> igniteCacheOperations;
    private final IgniteQueryMethod<T, ID> queryMethod;

    public IgniteRepositoryQuery(IgniteCacheOperations<T, ID> igniteCacheOperations, IgniteQueryMethod<T, ID> queryMethod) {
        this.igniteCacheOperations = igniteCacheOperations;
        this.queryMethod = queryMethod;
    }

    @Override
    public Object execute(Object[] parameters) {
        ParametersParameterAccessor accessor = new ParametersParameterAccessor(queryMethod.getParameters(), parameters);

        return null;
    }

    @Override
    public IgniteQueryMethod<T, ID> getQueryMethod() {
        return queryMethod;
    }
}
