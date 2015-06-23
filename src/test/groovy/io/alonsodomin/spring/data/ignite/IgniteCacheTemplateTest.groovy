package io.alonsodomin.spring.data.ignite

import org.apache.ignite.IgniteCache
import spock.lang.Specification

import javax.cache.Cache
import java.util.function.Consumer

/**
 * Created by domingueza on 22/06/15.
 */
class IgniteCacheTemplateTest extends Specification {

    IgniteCacheTemplate<String, String> igniteCacheTemplate

    IgniteCache<String, String> mockCache

    void setup() {
        mockCache = Mock(IgniteCache)
        igniteCacheTemplate = new IgniteCacheTemplate<>(mockCache)
    }

    void 'on getById then do a get on the underlying cache'() {
        given:
            String givenId = 'foo'
            String expectedValue = 'bar'

        when:
            String returnedValue = igniteCacheTemplate.getById(givenId)

        then:
            1 * mockCache.get(givenId) >> expectedValue
            0 * _

        and:
            returnedValue == expectedValue
    }

    void 'on fetchAll iterate the cache and build a list with the values'() {
        given:
            String givenValue = 'bar'

        and:
            List<String> expectedValues = [ givenValue ]

        and:
            Cache.Entry<String, String> expectedEntry = Mock(Cache.Entry)

        when:
            Iterable<String> returnedValues = igniteCacheTemplate.fetchAll()

        then:
            1 * mockCache.forEach(_ as Consumer) >> { args ->
                Consumer<Cache.Entry<String, String>> consumer = args[0]
                consumer.accept(expectedEntry)
            }
            1 * expectedEntry.value >> givenValue
            0 * _

        and:
            returnedValues == expectedValues
    }

    void 'on fetch do a getAll in the cache and return the values of the returned map'() {
        given:
            String givenId = 'foo'
            String givenValue = 'bar'

        and:
            Iterable<String> idsToLookUp = [ givenId ]
            Map<String, String> mapToReturn = [ "${givenId}" : givenValue ]

        and:
            Iterable<String> expectedValues = [ givenValue ]

        when:
            Iterable<String> returnedValues = igniteCacheTemplate.fetch(idsToLookUp)

        then:
            1 * mockCache.getAll(_ as Set) >> { args ->
                Set<String> valuesToFetch = args[0]
                assert valuesToFetch.contains(givenId)

                return mapToReturn
            }
            0 * _

        and:
            returnedValues.containsAll(expectedValues.toArray())
    }

    void 'on exists verify that the cache contains the given id'() {
        given:
            String givenId = 'foo'
            boolean expectedResult = true

        when:
            boolean returnedResult = igniteCacheTemplate.exists(givenId)

        then:
            1 * mockCache.containsKey(givenId) >> expectedResult
            0 * _

        and:
            returnedResult == expectedResult
    }

    void 'on count return number of items in the cache'() {
        given:
            long expectedCount = 34

        when:
            long returnedCount = igniteCacheTemplate.count()

        then:
            1 * mockCache.size() >> expectedCount
            0 * _

        and:
            returnedCount == expectedCount
    }

    void 'on clear then clear the underlying cache'() {
        when:
            igniteCacheTemplate.clear()

        then:
            1 * mockCache.clear()
            0 * _
    }

    void 'on save add the item to the cache'() {
        given:
            String givenId = 'foo'
            String givenValue = 'bar'
            String expectedOldValue = 'old'

        when:
            String returnedOldValue = igniteCacheTemplate.save(givenId, givenValue)

        then:
            1 * mockCache.getAndPut(givenId, givenValue) >> expectedOldValue
            0 * _

        and:
            returnedOldValue == expectedOldValue
    }

    void 'on save map perform a putAll in the underlying cache'() {
        given:
            String givenId = 'foo'
            String givenValue = 'bar'
            Map<String, String> valuesToSave = [ "${givenId}" : givenValue ]

        when:
            igniteCacheTemplate.save(valuesToSave)

        then:
            1 * mockCache.putAll(valuesToSave)
            0 * _
    }

}
