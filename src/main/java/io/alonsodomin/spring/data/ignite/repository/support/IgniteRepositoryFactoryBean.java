package io.alonsodomin.spring.data.ignite.repository.support;

import org.apache.ignite.IgniteCache;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import java.io.Serializable;

/**
 * Created by domingueza on 22/06/15.
 */
public class IgniteRepositoryFactoryBean<T extends Repository<S, ID>, S extends Serializable, ID extends Serializable> extends RepositoryFactoryBeanSupport<T, S, ID> {

    private IgniteCache<ID, S> igniteCache;

    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {
        return null;
    }

}
