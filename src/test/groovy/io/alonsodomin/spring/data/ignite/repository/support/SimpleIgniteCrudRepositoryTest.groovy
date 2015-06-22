package io.alonsodomin.spring.data.ignite.repository.support

import groovy.transform.EqualsAndHashCode
import io.alonsodomin.spring.data.ignite.IgniteCacheOperations
import org.springframework.data.annotation.Id
import spock.lang.Specification

/**
 * Created by domingueza on 22/06/15.
 */
class SimpleIgniteCrudRepositoryTest extends Specification {

    SimpleIgniteCrudRepository<TestEntity, String> simpleIgniteCrudRepository

    IgniteEntityInformation<TestEntity, String> mockIgniteEntityInformation
    IgniteCacheOperations<TestEntity, String> mockIgniteCacheOperations

    void setup() {
        mockIgniteEntityInformation = Mock(IgniteEntityInformation)
        mockIgniteCacheOperations = Mock(IgniteCacheOperations)

        simpleIgniteCrudRepository = new SimpleIgniteCrudRepository<>(mockIgniteEntityInformation, mockIgniteCacheOperations)
    }

    void 'on findOne invoke getById on the cache'() {
        given:
            String givenId = 'foo'
            TestEntity expectedEntity = new TestEntity(id: givenId)

        when:
            TestEntity returnedEntity = simpleIgniteCrudRepository.findOne(givenId)

        then:
            1 * mockIgniteCacheOperations.getById(givenId) >> expectedEntity
            0 * _

        and:
            returnedEntity == expectedEntity
    }

    @EqualsAndHashCode
    static class TestEntity {

        @Id
        String id

    }

}
