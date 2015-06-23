package io.alonsodomin.spring.data.ignite.test

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.springframework.data.annotation.Id

/**
 * Created by domingueza on 23/06/15.
 */
@EqualsAndHashCode
@ToString
class TestEntity {

    @Id
    String id

}
