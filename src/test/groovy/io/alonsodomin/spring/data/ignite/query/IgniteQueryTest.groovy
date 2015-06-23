package io.alonsodomin.spring.data.ignite.query

import io.alonsodomin.spring.data.ignite.core.IgniteCacheOperations
import io.alonsodomin.spring.data.ignite.test.TestEntity
import org.springframework.data.repository.query.QueryMethod
import spock.lang.Specification

/**
 * Created by aalonsodominguez on 23/06/15.
 */
class IgniteQueryTest extends Specification {

    IgniteQuery<TestEntity, String> igniteQuery

    IgniteCacheOperations<TestEntity, String> mockCacheOperations
    IgniteQueryMethod<TestEntity, String> mockQueryMethod

    void setup() {
        mockCacheOperations = Mock(IgniteCacheOperations)
        mockQueryMethod = Mock(IgniteQueryMethod)

        igniteQuery = new IgniteQuery<>(mockCacheOperations, mockQueryMethod)
    }

    void 'on getQueryMethod return the query method used in the constructor'() {
        when:
            QueryMethod returnedQueryMethod = igniteQuery.getQueryMethod()

        then:
            returnedQueryMethod == mockQueryMethod as QueryMethod
    }

}
