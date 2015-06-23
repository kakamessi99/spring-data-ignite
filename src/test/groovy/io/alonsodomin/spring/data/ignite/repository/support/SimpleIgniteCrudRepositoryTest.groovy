package io.alonsodomin.spring.data.ignite.repository.support

import io.alonsodomin.spring.data.ignite.core.IgniteCacheOperations
import io.alonsodomin.spring.data.ignite.core.IgniteEntityInformation
import io.alonsodomin.spring.data.ignite.test.TestEntity
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

    void 'on save delegate the the underlying operations implementation'() {
        given:
            String givenId = 'foo'
            TestEntity newEntity = new TestEntity(id: givenId)
            TestEntity expectedOldEntity = new TestEntity(id: givenId)

        when:
            TestEntity returnedEntity = simpleIgniteCrudRepository.save(newEntity)

        then:
            1 * mockIgniteEntityInformation.getId(newEntity) >> givenId
            1 * mockIgniteCacheOperations.save(givenId, newEntity) >> expectedOldEntity
            0 * _

        and:
            returnedEntity == newEntity
    }

    void 'on save an iterable then delegate to the save map operation'() {
        given:
            String givenId = 'foo'
            TestEntity newEntity = new TestEntity(id: givenId)
            List<TestEntity> entities = [ newEntity ]

        when:
            Iterable<TestEntity> returnedEntities = simpleIgniteCrudRepository.save(entities)

        then:
            1 * mockIgniteEntityInformation.getId(newEntity) >> givenId
            1 * mockIgniteCacheOperations.save(_ as Map) >> { args ->
                Map<String, TestEntity> mapArg = (Map) args[0]
                assert mapArg.get(givenId) == newEntity
            }
            0 * _

        and:
            returnedEntities == entities
    }

    void 'on findAll delegate to the underlying operations implementation'() {
        given:
            String givenId = 'foo'
            TestEntity givenEntity = new TestEntity(id: givenId)

        and:
            List<TestEntity> expectedEntities = [ givenEntity ]

        when:
            Iterable<TestEntity> returnedEntities = simpleIgniteCrudRepository.findAll()

        then:
            1 * mockIgniteCacheOperations.fetchAll() >> expectedEntities
            0 * _

        and:
            returnedEntities == expectedEntities
    }

    void 'on findAll with specific ids then perform a fetch in the underlying operations implementation'() {
        given:
            String givenId = 'foo'
            TestEntity givenEntity = new TestEntity(id: givenId)

        and:
            List<String> idsToFetch = [ givenId ]

        and:
            List<TestEntity> expectedEntities = [ givenEntity ]

        when:
            Iterable<TestEntity> returnedEntities = simpleIgniteCrudRepository.findAll(idsToFetch)

        then:
            1 * mockIgniteCacheOperations.fetch(idsToFetch) >> expectedEntities
            0 * _

        and:
            returnedEntities == expectedEntities
    }

    void 'on exists delegate to the underlying operations implementation'() {
        given:
            String givenId = 'foo'
            boolean expectedResult = true

        when:
            boolean returnedResult = simpleIgniteCrudRepository.exists(givenId)

        then:
            1 * mockIgniteCacheOperations.exists(givenId) >> expectedResult
            0 * _

        and:
            returnedResult == expectedResult
    }

    void 'on delete by id delegate to the underlying cache'() {
        given:
            String givenId = 'foo'

        when:
            simpleIgniteCrudRepository.delete(givenId)

        then:
            1 * mockIgniteCacheOperations.remove(givenId)
            0 * _
    }

    void 'on delete entity read the ID and delegate to the underlying cache'() {
        given:
            String givenId = 'foo'
            TestEntity givenEntity = new TestEntity(id: givenId)

        when:
            simpleIgniteCrudRepository.delete(givenEntity)

        then:
            1 * mockIgniteEntityInformation.getId(givenEntity) >> givenId
            1 * mockIgniteCacheOperations.remove(givenId)
            0 * _
    }

    void 'on delete all given entities map them to ids and delegate to the underlying cache'() {
        given:
            String givenId = 'foo'
            TestEntity givenEntity = new TestEntity(id: givenId)
            List<TestEntity> entitiesToDelete = [ givenEntity ]

        when:
            simpleIgniteCrudRepository.delete((Iterable<TestEntity>) entitiesToDelete)

        then:
            1 * mockIgniteEntityInformation.getId(givenEntity) >> givenId
            1 * mockIgniteCacheOperations.remove(_ as Set) >> { args ->
                Set<String> idsToDelete = args[0]
                assert idsToDelete.contains(givenId)
            }
            0 * _
    }

    void 'on count get the size of the underlying cache'() {
        given:
            long expectedCount = 32

        when:
            long returnedCount = simpleIgniteCrudRepository.count()

        then:
            1 * mockIgniteCacheOperations.count() >> expectedCount
            0 * _

        and:
            returnedCount == expectedCount
    }

    void 'on deleteAll then clear the cache'() {
        when:
            simpleIgniteCrudRepository.deleteAll()

        then:
            1 * mockIgniteCacheOperations.clear()
            0 * _
    }

}
