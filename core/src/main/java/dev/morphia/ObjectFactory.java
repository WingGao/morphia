package dev.morphia;

import dev.morphia.mapping.Mapper;
import dev.morphia.mapping.codec.pojo.PropertyModel;
import org.bson.Document;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The ObjectFactory is used by morphia to create instances of classes which can be customized to fit a particular applications needs.
 */
public interface ObjectFactory {
    /**
     * Creates an instance of the given class.
     *
     * @param clazz type class to instantiate
     * @param <T>   the type of the entity
     * @return the new instance
     */
    <T> T createInstance(Class<T> clazz);

    /**
     * Creates an instance of the class defined in the discriminator field in the document passed in. If that field
     * is missing, the given Class is used instead.
     *
     * @param clazz    type class to instantiate
     * @param document the state to populate the new instance with
     * @param <T>      the type of the entity
     * @return the new instance
     */
    <T> T createInstance(Class<T> clazz, Document document);

    /**
     * Creates an instance of the class defined in the discriminator field in the document passed in. If that field
     * is missing, morphia attempts to the MappedField to determine which concrete class to instantiate.
     *
     * @param mapper   the Mapper to use
     * @param mf       the MappedField to consult when creating the instance
     * @param document the state to populate the new instance with
     * @return the new instance
     */
    Object createInstance(Mapper mapper, PropertyModel mf, Document document);

    /**
     * Defines how morphia creates a List object.
     *
     * @param mf the MappedField containing any metadata that might define the type of the List to create
     * @return the List
     */
    List createList(PropertyModel mf);

    /**
     * Defines how morphia creates a Map object.
     *
     * @param mf the MappedField containing any metadata that might define the type of the Map to create
     * @return the Map
     */
    Map createMap(PropertyModel mf);

    /**
     * Defines how morphia creates a Set object.
     *
     * @param mf the MappedField containing any metadata that might define the type of the Set to create
     * @return the Set
     */
    Set createSet(PropertyModel mf);
}
