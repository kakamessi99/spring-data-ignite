package io.alonsodomin.spring.data.ignite.repository.query;

import io.alonsodomin.spring.data.ignite.core.IgniteEntityInformation;
import org.apache.ignite.cache.query.Query;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.data.repository.query.ParameterAccessor;
import org.springframework.data.repository.query.parser.AbstractQueryCreator;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.data.repository.query.parser.PartTree;

import javax.cache.Cache;
import java.io.Serializable;
import java.util.Iterator;

/**
 * Created by aalonsodominguez on 23/06/15.
 */
public class IgniteQueryCreator<T, ID extends Serializable>
        extends AbstractQueryCreator<Query<Cache.Entry<ID, T>>, IgniteBiPredicate<ID, T>> {

    private final IgniteEntityInformation<T, ID> entityInformation;

    public IgniteQueryCreator(PartTree tree, ParameterAccessor parameters, IgniteEntityInformation<T, ID> entityInformation) {
        super(tree, parameters);
        this.entityInformation = entityInformation;
    }

    @Override
    protected IgniteBiPredicate<ID, T> create(Part part, Iterator<Object> iterator) {
        return createPredicate(part, iterator);
    }

    @Override
    protected IgniteBiPredicate<ID, T> and(Part part, IgniteBiPredicate<ID, T> base, Iterator<Object> iterator) {
        IgniteBiPredicate<ID, T> predicate = createPredicate(part, iterator);
        return IgniteBiPredicates.and(base, predicate);
    }

    @Override
    protected IgniteBiPredicate<ID, T> or(IgniteBiPredicate<ID, T> base, IgniteBiPredicate<ID, T> criteria) {
        return IgniteBiPredicates.or(base, criteria);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Query<Cache.Entry<ID, T>> complete(IgniteBiPredicate<ID, T> criteria, Sort sort) {
        return new ScanQuery<>(criteria);
    }

    private IgniteBiPredicate<ID, T> createPredicate(Part part, Iterator<Object> iterator) {
        PropertyPath leafNodeProperty = part.getProperty().getLeafProperty();

        String leafNodePropertyName = leafNodeProperty.toDotPath();
        int dotIdx;
        if ((dotIdx = leafNodePropertyName.lastIndexOf('.')) >= 0) {
            leafNodePropertyName = leafNodePropertyName.substring(dotIdx);
        }

        switch (part.getType()) {
            case SIMPLE_PROPERTY:
                return IgniteBiPredicates.propertyEquals(entityInformation, leafNodePropertyName, iterator.next(), leafNodeProperty.getType());
        }
        return null;
    }

}
