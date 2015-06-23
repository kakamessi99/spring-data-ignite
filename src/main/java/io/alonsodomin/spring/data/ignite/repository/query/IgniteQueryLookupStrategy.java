package io.alonsodomin.spring.data.ignite.repository.query;

import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.RepositoryQuery;

import java.lang.reflect.Method;

/**
 * Created by aalonsodominguez on 23/06/15.
 */
public class IgniteQueryLookupStrategy implements QueryLookupStrategy {

    @Override
    public RepositoryQuery resolveQuery(Method method, RepositoryMetadata metadata, NamedQueries namedQueries) {
        return null;
    }

}
