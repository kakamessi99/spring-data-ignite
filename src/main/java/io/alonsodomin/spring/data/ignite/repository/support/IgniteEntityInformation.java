package io.alonsodomin.spring.data.ignite.repository.support;

import org.springframework.data.repository.core.EntityInformation;

import java.io.Serializable;

/**
 * Created by domingueza on 22/06/15.
 */
public interface IgniteEntityInformation<T, ID extends Serializable> extends EntityInformation<T, ID> {
}
