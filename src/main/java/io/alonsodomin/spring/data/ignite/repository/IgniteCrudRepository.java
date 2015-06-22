package io.alonsodomin.spring.data.ignite.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by domingueza on 22/06/15.
 */
@NoRepositoryBean
public interface IgniteCrudRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {
}
