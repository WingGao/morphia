package dev.morphia.geo;

import dev.morphia.mapping.MappedField;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dev.morphia.geo.GeoJsonType.LINE_STRING;
import static dev.morphia.geo.GeoJsonType.MULTI_LINE_STRING;
import static dev.morphia.geo.GeoJsonType.MULTI_POINT;
import static dev.morphia.geo.GeoJsonType.MULTI_POLYGON;
import static dev.morphia.geo.GeoJsonType.POINT;
import static dev.morphia.geo.GeoJsonType.POLYGON;

/**
 * Converter that understands most Geometry instances are effectively just lists of either other geometry objects or double coordinates.
 * Recursively encodes and decodes Geometry objects, but needs to be instantiated with a List of GeometryFactory instances that represented
 * the hierarchy of Geometries that make up the required Geometry object.
 * <p/>
 * Overridden by subclasses to define exact behaviour for specific Geometry concrete classes.
 */
public class GeometryShapeConverter<T> implements Codec<T> {
    private final GeoJsonType geoJsonType;
    private final List<GeometryFactory> factories;

    GeometryShapeConverter(final GeoJsonType... geoJsonTypes) {
        geoJsonType = geoJsonTypes[0];
        this.factories = Arrays.asList(geoJsonTypes);
    }

    public Geometry decode(final Class<?> targetClass, final Object fromDocument, final MappedField optionalExtraInfo) {
        return decodeObject((List) ((Document) fromDocument).get("coordinates"), factories);
    }

    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        if (value != null) {
            Object encodedObjects = encodeObjects(((Geometry) value).getCoordinates());
            return new Document("type", geoJsonType.getType())
                       .append("coordinates", encodedObjects);
        } else {
            return null;
        }
    }

    @Override
    public T decode(final BsonReader reader, final DecoderContext decoderContext) {
        return null;
    }

    @Override
    public void encode(final BsonWriter writer, final T value, final EncoderContext encoderContext) {

    }

    @Override
    public Class<T> getEncoderClass() {
        return null;
    }

    /*
     * We're expecting a List that can be turned into a geometry using a series of factories
      */
    @SuppressWarnings("unchecked") // always have unchecked casts when dealing with raw classes
    private Geometry decodeObject(final List mongoDBGeometry, final List<GeometryFactory> geometryFactories) {
        GeometryFactory factory = geometryFactories.get(0);
        if (geometryFactories.size() == 1) {
            // This should be the last list, so no need to decode further
            return factory.createGeometry(mongoDBGeometry);
        } else {
            List<Geometry> decodedObjects = new ArrayList<>();
            for (final Object objectThatNeedsDecoding : mongoDBGeometry) {
                // MongoDB geometries are lists of lists of lists...
                decodedObjects.add(decodeObject((List) objectThatNeedsDecoding,
                                                geometryFactories.subList(1, geometryFactories.size())));
            }
            return factory.createGeometry(decodedObjects);
        }
    }

    private Object encodeObjects(final List value) {
        List<Object> encodedObjects = new ArrayList<>();
        for (final Object object : value) {
            if (object instanceof Geometry) {
                //iterate through the list of geometry objects recursively until you find the lowest-level
                encodedObjects.add(encodeObjects(((Geometry) object).getCoordinates()));
            } else {
//                encodedObjects.add(getMapper().getConverters().encode(object));
            }
        }
        return encodedObjects;
    }

    /**
     * Extends and therefore configures GeometryShapeConverter to provide the specific configuration for converting MultiPolygon objects to
     * and from <a href="http://geojson.org/geojson-spec.html#id7">MongoDB representations</a> of the GeoJson.
     */
    public static class MultiPolygonConverter extends GeometryShapeConverter {
        /**
         * Creates a new MultiPolygonConverter.
         */
        public MultiPolygonConverter() {
            super(MULTI_POLYGON, POLYGON, LINE_STRING, POINT);
        }
    }

    /**
     * Defines a new PolygonConverter.  This extends and therefore configures GeometryShapeConverter to provide the specific
     * configuration for converting Polygon objects to and from <a href="http://geojson.org/geojson-spec.html#id4">MongoDB
     * representations</a> of the GeoJson.
     */
    public static class PolygonConverter extends GeometryShapeConverter {
        /**
         * Creates a new PolygonConverter.
         */
        public PolygonConverter() {
            super(POLYGON, LINE_STRING, POINT);
        }
    }

    /**
     * Defines a new MultiLineStringConverter.  This extends and therefore configures GeometryShapeConverter to provide the specific
     * configuration for converting MultiLineString objects to and from <a href="http://geojson.org/geojson-spec.html#id6">MongoDB
     * representations</a> of the GeoJson.
     */
    public static class MultiLineStringConverter extends GeometryShapeConverter {
        /**
         * Creates a new MultiLineStringConverter.
         */
        public MultiLineStringConverter() {
            super(MULTI_LINE_STRING, LINE_STRING, POINT);
        }
    }

    /**
     * Defines a new MultiPointConverter. This extends and therefore configures GeometryShapeConverter to provide the specific
     * configuration for converting MultiPoint objects to and from <a href="http://geojson.org/geojson-spec.html#id5">MongoDB
     * representations</a> of the GeoJson.
     */
    public static class MultiPointConverter extends GeometryShapeConverter {
        /**
         * Creates a new MultiPointConverter.
         */
        public MultiPointConverter() {
            super(MULTI_POINT, POINT);
        }
    }

    /**
     * Defines a new LineStringConverter. This extends and therefore configures GeometryShapeConverter to provide the specific
     * configuration for converting LineString objects to and from <a href="http://geojson.org/geojson-spec.html#id3">MongoDB
     * representations</a> of the GeoJson.
     */
    public static class LineStringConverter extends GeometryShapeConverter {
        /**
         * Creates a new LineStringConverter.
         */
        public LineStringConverter() {
            super(LINE_STRING, POINT);
        }
    }

    /**
     * Defines a new PointCodec. This extends and therefore configures GeometryShapeConverter to provide the specific configuration
     * for converting Point objects to and from <a href="http://geojson.org/geojson-spec.html#id3">MongoDB representations</a> of the
     * GeoJson.
     */
    public static class PointCodec extends GeometryShapeConverter {
        /**
         * Creates a new PointCodec.
         */
        public PointCodec() {
            super(POINT);
        }
    }
}
