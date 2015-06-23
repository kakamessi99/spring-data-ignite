package io.alonsodomin.spring.data.ignite.repository.support

import io.alonsodomin.spring.data.ignite.test.TestEntity
import spock.lang.Specification

/**
 * Created by domingueza on 22/06/15.
 */
class IgniteEntityMetadataTest extends Specification {

    IgniteEntityMetadata<TestEntity, String> entityMetadata

    void setup() {
        entityMetadata = new IgniteEntityMetadata(TestEntity)
    }

    void 'on getIdType return the type of the @Id field'() {
        when:
            Class<?> idType = entityMetadata.getIdType()

        then:
            idType == String
    }

    void 'on getId return the value of the @Id property'() {
        given:
            String expectedId = 'foo'
            TestEntity entity = new TestEntity(id: expectedId)

        when:
            String returnedId = entityMetadata.getId(entity)

        then:
            returnedId == expectedId
    }

}
