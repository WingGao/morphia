package dev.morphia.mapping.codec;

import dev.morphia.annotations.internal.MorphiaInternal;
import dev.morphia.mapping.MappingException;
import org.bson.codecs.pojo.PropertyAccessor;

import java.lang.reflect.Method;

/**
 * @morphia.internal
 */
@MorphiaInternal
public class MethodAccessor implements PropertyAccessor<Object> {
    private final Method getter;
    private final Method setter;

    /**
     * @param getter
     * @param setter
     * @morphia.internal
     */
    @MorphiaInternal
    public MethodAccessor(Method getter, Method setter) {
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public <S> Object get(S instance) {
        try {
            return getter.invoke(instance);
        } catch (ReflectiveOperationException e) {
            throw new MappingException(e.getMessage(), e);
        }
    }

    @Override
    public <S> void set(S instance, Object value) {
        try {
            setter.invoke(instance, value);
        } catch (ReflectiveOperationException e) {
            throw new MappingException(e.getMessage(), e);
        }
    }
}
