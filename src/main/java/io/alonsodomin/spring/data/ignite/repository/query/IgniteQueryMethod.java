package io.alonsodomin.spring.data.ignite.repository.query;

import io.alonsodomin.spring.data.ignite.core.IgniteEntityMetadataSupport;
import org.springframework.data.repository.core.EntityMetadata;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryMethod;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by aalonsodominguez on 23/06/15.
 */
public class IgniteQueryMethod<T, ID extends Serializable> extends QueryMethod {

    /**
     * Creates a new {@link QueryMethod} from the given parameters. Looks up the correct query to use for following
     * invocations of the method given.
     *
     * @param method   must not be {@literal null}
     * @param metadata must not be {@literal null}
     */
    public IgniteQueryMethod(Method method, RepositoryMetadata metadata) {
        super(method, metadata);
    }

    @Override
    @SuppressWarnings("unchecked")
    public EntityMetadata<T> getEntityInformation() {
        return new IgniteEntityMetadataSupport<>((Class<T>) getDomainClass());
    }

}
