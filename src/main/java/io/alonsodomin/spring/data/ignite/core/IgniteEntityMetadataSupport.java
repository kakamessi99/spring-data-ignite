package io.alonsodomin.spring.data.ignite.core;

import org.springframework.data.annotation.Id;
import org.springframework.data.repository.core.support.AbstractEntityInformation;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by domingueza on 22/06/15.
 */
public class IgniteEntityMetadataSupport<T, ID extends Serializable>
        extends AbstractEntityInformation<T, ID>
        implements IgniteEntityInformation<T, ID> {

    private Method idGetterMethod;
    private Field idField;

    public IgniteEntityMetadataSupport(Class<T> domainClass) {
        super(domainClass);

        ReflectionUtils.doWithMethods(domainClass, method -> {
            if (method.isAnnotationPresent(Id.class)) {
                idGetterMethod = method;
            }
        });
        if (idGetterMethod == null) {
            ReflectionUtils.doWithFields(domainClass, field -> {
                if (field.isAnnotationPresent(Id.class)) {
                    idField = field;
                }
            });
        }

        Assert.isTrue((idGetterMethod != null || idField != null), String.format("No field or method annotated with %s found!", Id.class.getName()));

        if (idGetterMethod != null) {
            ReflectionUtils.makeAccessible(idGetterMethod);
        }
        if (idField != null) {
            ReflectionUtils.makeAccessible(idField);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ID getId(T entity) {
        if (entity == null) {
            return null;
        }

        if (idGetterMethod != null) {
            return (ID) ReflectionUtils.invokeMethod(idGetterMethod, entity);
        } else {
            return (ID) ReflectionUtils.getField(idField, entity);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<ID> getIdType() {
        return (Class<ID>) (idGetterMethod != null ? idGetterMethod.getReturnType() : idField.getType());
    }
}
