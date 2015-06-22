package io.alonsodomin.spring.data.ignite.repository.support;

import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import java.io.Serializable;

/**
 * Created by domingueza on 22/06/15.
 */
public class IgniteRepositoryFactory extends RepositoryFactorySupport {

    @Override
    public <T, ID extends Serializable> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        return new IgniteEntityMetadata<>(domainClass);
    }

    @Override
    protected Object getTargetRepository(RepositoryMetadata metadata) {
        return null;
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return null;
    }
}
