package io.alonsodomin.spring.data.ignite.repository.query;

import io.alonsodomin.spring.data.ignite.core.IgniteEntityInformation;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Created by aalonsodominguez on 24/06/15.
 */
public final class IgniteBiPredicates {

    public static <K, V> IgniteBiPredicate<K, V> noOp() {
        return (k, v) -> true;
    }

    public static <K, V> IgniteBiPredicate<K, V> and(IgniteBiPredicate<K, V> left, IgniteBiPredicate<K, V> right) {
        return (k, v) -> left.apply(k, v) && right.apply(k, v);
    }

    public static <K, V> IgniteBiPredicate<K, V> or(IgniteBiPredicate<K, V> left, IgniteBiPredicate<K, V> right) {
        return (k, v) -> left.apply(k, v) || right.apply(k, v);
    }

    public static <T, ID extends Serializable> IgniteBiPredicate<ID, T> propertyEquals(IgniteEntityInformation<T, ID> entityInformation, String propertyName, Object value, Class<?> type) {
        Field field = ReflectionUtils.findField(entityInformation.getJavaType(), propertyName, type);
        ReflectionUtils.makeAccessible(field);

        return  (id, obj) -> {
            Object currentValue = ReflectionUtils.getField(field, obj);
            return Objects.equals(currentValue, value);
        };
    }

    private IgniteBiPredicates() { }

}
