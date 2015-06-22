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

}
