package io.alonsodomin.spring.data.ignite

import org.apache.ignite.IgniteCache
import spock.lang.Specification

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

}
