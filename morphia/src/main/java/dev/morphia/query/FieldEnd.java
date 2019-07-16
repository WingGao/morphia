package dev.morphia.query;


import dev.morphia.geo.CoordinateReferenceSystem;
import dev.morphia.geo.Geometry;
import dev.morphia.geo.MultiPolygon;
import dev.morphia.geo.Point;
import dev.morphia.geo.Polygon;

/**
 * Represents a document field in a query and presents the operations available to querying against that field.
 *
 * @param <T> the type of the FieldEnd
 */
public interface FieldEnd<T> {

    /**
     * Checks if a field contains a value
     *
     * @param string the value to check for
     * @return T
     * @mongodb.driver.manual reference/operator/query/regex/ $regex
     */
    T contains(String string);

    /**
     * Checks if a field contains a value ignoring the case of the values
     *
     * @param string the value to check for
     * @return T
     * @mongodb.driver.manual reference/operator/query/regex/ $regex
     */
    T containsIgnoreCase(String string);

    /**
     * Checks that a field does not exist in a document
     *
     * @return T
     * @mongodb.driver.manual reference/operator/query/exists/ $exists
     */
    T doesNotExist();

    /**
     * Checks that a field ends with a value
     *
     * @param suffix the value to check
     * @return T
     * @mongodb.driver.manual reference/operator/query/regex/ $regex
     */
    T endsWith(String suffix);

    /**
     * Checks that a field ends with a value ignoring the case of the values
     *
     * @param suffix the value to check
     * @return T
     * @mongodb.driver.manual reference/operator/query/regex/ $regex
     */
    T endsWithIgnoreCase(String suffix);

    /**
     * Checks that a field equals a value
     *
     * @param val the value to check
     * @return T
     * @mongodb.driver.manual reference/operator/query/eq/ $eq
     */
    T equal(Object val);

    /**
     * Checks that a field equals a value
     *
     * @param val the value to check
     * @return T
     * @mongodb.driver.manual reference/operator/query/eq/ $eq
     */
    T equalIgnoreCase(Object val);

    /**
     * Checks that a field exists in a document
     *
     * @return T
     * @mongodb.driver.manual reference/operator/query/exists/ $exists
     */
    T exists();

    /**
     * Checks that a field is greater than the value given
     *
     * @param val the value to check against
     * @return T
     * @mongodb.driver.manual reference/operator/query/gt/ $gt
     */
    T greaterThan(Object val);

    /**
     * Checks that a field is greater than or equal to the value given
     *
     * @param val the value to check against
     * @return T
     * @mongodb.driver.manual reference/operator/query/gte/ $gte
     */
    T greaterThanOrEq(Object val);

    /**
     * Checks that a field has all of the values listed.
     *
     * @param values the values to check against
     * @return T
     * @mongodb.driver.manual reference/operator/query/all/ $all
     */
    T hasAllOf(Iterable<?> values);

    /**
     * Checks that a field has any of the values listed.
     *
     * @param values the values to check against
     * @return T
     * @mongodb.driver.manual reference/operator/query/in/ $in
     */
    T hasAnyOf(Iterable<?> values);

    /**
     * Checks that a field has none of the values listed.
     *
     * @param values the values to check against
     * @return T
     * @mongodb.driver.manual reference/operator/query/nin/ $nin
     */
    T hasNoneOf(Iterable<?> values);

    /**
     * Checks that a field matches the provided query definition
     *
     * @param query the query to find certain field values
     * @return T
     * @mongodb.driver.manual reference/operator/query/elemMatch/ $elemMatch
     */
    T elemMatch(Query query);

    /**
     * Checks that a field has the value listed.
     *
     * @param val the value to check against
     * @return T
     * @mongodb.driver.manual reference/operator/query/eq/ $eq
     */
    T hasThisOne(Object val);

    /**
     * Synonym for {@link #hasAnyOf(Iterable)}
     *
     * @param values the values to check against
     * @return T
     * @mongodb.driver.manual reference/operator/query/in/ $in
     */
    T in(Iterable<?> values);

    /**
     * This performs a $geoIntersects query, searching documents containing any sort of GeoJson field and returning those where the given
     * geometry intersects with the document shape.  This includes cases where the data and the specified object share an edge.
     *
     * @param geometry the shape to use to query for any stored shapes that intersect
     * @return T
     * @mongodb.driver.manual reference/operator/query/geoIntersects/ $geoIntersects
     * @mongodb.server.release 2.4
     * @deprecated use the driver provided geo types instead
     */
    @Deprecated(since = "2.0", forRemoval = true)
    default T intersects(Geometry geometry) {
        return intersects(geometry.convert());
    }

    /**
     * This performs a $geoIntersects query, searching documents containing any sort of GeoJson field and returning those where the given
     * geometry intersects with the document shape.  This includes cases where the data and the specified object share an edge.
     *
     * @param geometry the shape to use to query for any stored shapes that intersect
     * @return T
     * @mongodb.driver.manual reference/operator/query/geoIntersects/ $geoIntersects
     * @mongodb.server.release 2.4
     * @since 2.0
     */
    T intersects(com.mongodb.client.model.geojson.Geometry geometry);

    /**
     * This performs a $geoIntersects query, searching documents containing any sort of GeoJson field and returning those where the given
     * geometry intersects with the document shape.  This includes cases where the data and the specified object share an edge.
     *
     * @param geometry the shape to use to query for any stored shapes that intersect
     * @param crs      the coordinate reference system to use with the query
     * @return T
     * @mongodb.driver.manual reference/operator/query/geometry $geometry
     * @mongodb.server.release 2.4
     * @deprecated Use the driver supplied geo types instead
     */
    @Deprecated(since = "2.0", forRemoval = true)
    default T intersects(Geometry geometry, final CoordinateReferenceSystem crs) {
        return intersects(geometry.convert(), crs.convert());
    }

    /**
     * This performs a $geoIntersects query, searching documents containing any sort of GeoJson field and returning those where the given
     * geometry intersects with the document shape.  This includes cases where the data and the specified object share an edge.
     *
     * @param geometry the shape to use to query for any stored shapes that intersect
     * @param crs      the coordinate reference system to use with the query
     * @return T
     * @mongodb.driver.manual reference/operator/query/geometry $geometry
     * @mongodb.server.release 2.4
     * @since 2.0
     */
    T intersects(com.mongodb.client.model.geojson.Geometry geometry, final com.mongodb.client.model.geojson.CoordinateReferenceSystem crs);

    /**
     * Checks that a field is less than the value given
     *
     * @param val the value to check against
     * @return T
     * @mongodb.driver.manual reference/operator/query/lt/ $lt
     */
    T lessThan(Object val);

    /**
     * Checks that a field is less than or equal to the value given
     *
     * @param val the value to check against
     * @return T
     * @mongodb.driver.manual reference/operator/query/lte/ $lte
     */
    T lessThanOrEq(Object val);

    /**
     * Select documents where the value of a field divided by a divisor has the specified remainder (i.e. perform a modulo operation
     * to select documents)
     *
     * @param divisor   the divisor to apply
     * @param remainder the remainder to check for
     * @return T
     * @mongodb.driver.manual reference/operator/query/mod/ $mod
     */
    T mod(long divisor, long remainder);

    /**
     * Specifies a point for which a geospatial query returns the documents from nearest to farthest.
     *
     * @param longitude the longitude
     * @param latitude  the latitude
     * @return T
     * @mongodb.driver.manual reference/operator/query/near/ $near
     */
    T near(double longitude, double latitude);

    /**
     * Specifies a point for which a geospatial query returns the documents from nearest to farthest.
     *
     * @param longitude the longitude
     * @param latitude  the latitude
     * @param spherical if true, will use spherical geometry ($nearSphere) when analyzing documents
     * @return T
     * @mongodb.driver.manual reference/operator/query/near/ $near
     * @mongodb.driver.manual reference/operator/query/nearSphere/ $nearSphere
     */
    T near(double longitude, double latitude, boolean spherical);

    /**
     * Specifies a point for which a geospatial query returns the documents from nearest to farthest.
     *
     * @param longitude the longitude
     * @param latitude  the latitude
     * @param radius    the max distance to consider
     * @return T
     * @mongodb.driver.manual reference/operator/query/near/ $near
     */
    T near(double longitude, double latitude, double radius);

    /**
     * Specifies a point for which a geospatial query returns the documents from nearest to farthest.
     *
     * @param longitude the longitude
     * @param latitude  the latitude
     * @param radius    the max distance to consider
     * @param spherical if true, will use spherical geometry ($nearSphere) when analyzing documents
     * @return T
     * @mongodb.driver.manual reference/operator/query/near/ $near
     * @mongodb.driver.manual reference/operator/query/nearSphere/ $nearSphere
     */
    T near(double longitude, double latitude, double radius, boolean spherical);

    /**
     * This runs a $near query to check for documents geographically close to the given Point - this Point represents a GeoJSON point type.
     * These queries are only supported by MongoDB version 2.4 or greater.
     *
     * @param point the point to find results close to
     * @return T
     * @mongodb.driver.manual reference/operator/query/near/ $near
     * @deprecated Use the driver supplied geo types instead
     */
    @Deprecated(since = "2.0", forRemoval = true)
    default T near(Point point) {
        return near(point.convert());
    }

    /**
     * This runs a $near query to check for documents geographically close to the given Point - this Point represents a GeoJSON point type.
     * These queries are only supported by MongoDB version 2.4 or greater.
     *
     * @param point the point to find results close to
     * @return T
     * @mongodb.driver.manual reference/operator/query/near/ $near
     * @since 2.0
     */
    T near(com.mongodb.client.model.geojson.Point point);

    /**
     * Specifies a point for which a geospatial query returns the documents from nearest to farthest.
     *
     * @param point       the point to find results close to
     * @param maxDistance the maximum distance in meters from the point
     * @param minDistance the minimum distance in meters from the point
     * @return T
     * @mongodb.driver.manual reference/operator/query/near/ $near
     * @since 1.5
     * @deprecated Use the driver supplied geo types instead
     */
    @Deprecated(since = "2.0", forRemoval = true)
    default T near(Point point, Double maxDistance, Double minDistance) {
        return near(point.convert(), maxDistance, minDistance);
    }

    /**
     * Specifies a point for which a geospatial query returns the documents from nearest to farthest.
     *
     * @param point       the point to find results close to
     * @param maxDistance the maximum distance in meters from the point
     * @param minDistance the minimum distance in meters from the point
     * @return T
     * @mongodb.driver.manual reference/operator/query/near/ $near
     * @since 2.0
     */
    T near(com.mongodb.client.model.geojson.Point point, Double maxDistance, Double minDistance);

    /**
     * Specifies a point for which a geospatial query returns the documents from nearest to farthest.
     *
     * @param point the point to find results close to
     * @return T
     * @mongodb.driver.manual reference/operator/query/nearSphere/ $nearSphere
     * @since 1.5
     * @deprecated Use the driver supplied geo types instead
     */
    @Deprecated(since = "2.0", forRemoval = true)
    default T nearSphere(Point point) {
        return nearSphere(point.convert());
    }

    /**
     * Specifies a point for which a geospatial query returns the documents from nearest to farthest.
     *
     * @param point the point to find results close to
     * @return T
     * @mongodb.driver.manual reference/operator/query/nearSphere/ $nearSphere
     * @since 2.0
     */
    T nearSphere(com.mongodb.client.model.geojson.Point point);

    /**
     * Specifies a point for which a geospatial query returns the documents from nearest to farthest.
     *
     * @param point       the point to find results close to
     * @param maxDistance the maximum distance in meters from the point
     * @param minDistance the minimum distance in meters from the point
     * @return T
     * @mongodb.driver.manual reference/operator/query/nearSphere/ $nearSphere
     * @since 1.5
     * @deprecated Use the driver supplied geo types instead
     */
    @Deprecated(since = "2.0", forRemoval = true)
    default T nearSphere(Point point, Double maxDistance, Double minDistance) {
        return nearSphere(point.convert(), maxDistance, minDistance);
    }

    /**
     * Specifies a point for which a geospatial query returns the documents from nearest to farthest.
     *
     * @param point       the point to find results close to
     * @param maxDistance the maximum distance in meters from the point
     * @param minDistance the minimum distance in meters from the point
     * @return T
     * @mongodb.driver.manual reference/operator/query/nearSphere/ $nearSphere
     * @since 2.0
     */
    T nearSphere(com.mongodb.client.model.geojson.Point point, Double maxDistance, Double minDistance);

    /**
     * Negates the criteria applied to the field
     *
     * @return this
     */
    FieldEnd<T> not();

    /**
     * Checks that a field doesn't equal a value
     *
     * @param val the value to check
     * @return T
     * @mongodb.driver.manual reference/operator/query/ne/ $ne
     */
    T notEqual(Object val);

    /**
     * Synonym for {@link #hasNoneOf(Iterable)}
     *
     * @param values the values to check against
     * @return T
     * @mongodb.driver.manual reference/operator/query/nin/ $nin
     */
    T notIn(Iterable<?> values);

    /**
     * Checks the size of a field.
     *
     * @param val the value to check against
     * @return T
     * @mongodb.driver.manual reference/operator/query/size/ $size
     */
    T sizeEq(int val);

    /**
     * Checks that a field starts with a value
     *
     * @param prefix the value to check
     * @return T
     * @mongodb.driver.manual reference/operator/query/regex/ $regex
     */
    T startsWith(String prefix);

    /**
     * Checks that a field starts with a value ignoring the case of the values
     *
     * @param prefix the value to check
     * @return T
     * @mongodb.driver.manual reference/operator/query/regex/ $regex
     */
    T startsWithIgnoreCase(final String prefix);

    /**
     * Checks the type of a field
     *
     * @param type the value to check against
     * @return T
     */
    T type(Type type);

    /**
     * This implements the $geoWithin operator and is only compatible with mongo 2.4 or greater.
     *
     * @param shape the shape to check within
     * @return T
     * @mongodb.driver.manual reference/operator/query/geoWithin/ $geoWithin
     * @mongodb.server.release 2.4
     */
    T within(Shape shape);

    /**
     * This runs the $geoWithin query, returning documents with GeoJson fields whose area falls within the given boundary. When determining
     * inclusion, MongoDB considers the border of a shape to be part of the shape, subject to the precision of floating point numbers.
     *
     * @param boundary a polygon describing the boundary to search within.
     * @return T
     * @mongodb.driver.manual reference/operator/query/geoWithin/ $geoWithin
     * @mongodb.server.release 2.4
     * @deprecated Use the driver supplied geo types instead
     */
    @Deprecated(since = "2.0", forRemoval = true)
    default T within(Polygon boundary) {
        return within(boundary.convert());
    }

    /**
     * This runs the $geoWithin query, returning documents with GeoJson fields whose area falls within the given boundary. When determining
     * inclusion, MongoDB considers the border of a shape to be part of the shape, subject to the precision of floating point numbers.
     *
     * @param boundary a polygon describing the boundary to search within.
     * @return T
     * @mongodb.driver.manual reference/operator/query/geoWithin/ $geoWithin
     * @mongodb.server.release 2.4
     * @since 2.0
     */
    T within(com.mongodb.client.model.geojson.Polygon boundary);

    /**
     * This runs the $geoWithin query, returning documents with GeoJson fields whose area falls within the given boundaries. When
     * determining inclusion, MongoDB considers the border of a shape to be part of the shape, subject to the precision of floating point
     * numbers.
     *
     * @param boundaries a multi-polygon describing the areas to search within.
     * @return T
     * @mongodb.driver.manual reference/operator/query/geoWithin/ $geoWithin
     * @mongodb.server.release 2.6
     * @deprecated Use the driver supplied geo types instead
     */
    @Deprecated(since = "2.0", forRemoval = true)
    default T within(MultiPolygon boundaries) {
        return within(boundaries.convert());
    }

    /**
     * This runs the $geoWithin query, returning documents with GeoJson fields whose area falls within the given boundaries. When
     * determining inclusion, MongoDB considers the border of a shape to be part of the shape, subject to the precision of floating point
     * numbers.
     *
     * @param boundaries a multi-polygon describing the areas to search within.
     * @return T
     * @mongodb.driver.manual reference/operator/query/geoWithin/ $geoWithin
     * @mongodb.server.release 2.6
     * @since 2.0
     */
    T within(com.mongodb.client.model.geojson.MultiPolygon boundaries);

    /**
     * This runs the $geoWithin query, returning documents with GeoJson fields whose area falls within the given boundary. When determining
     * inclusion, MongoDB considers the border of a shape to be part of the shape, subject to the precision of floating point numbers.
     *
     * @param boundary a polygon describing the boundary to search within.
     * @param crs      the coordinate reference system to use
     * @return T
     * @mongodb.driver.manual reference/operator/query/geoWithin/ $geoWithin
     * @mongodb.server.release 2.4
     * @deprecated Use the driver supplied geo types instead
     */
    @Deprecated(since = "2.0", forRemoval = true)
    default T within(Polygon boundary, CoordinateReferenceSystem crs) {
        return within(boundary.convert(), crs.convert());
    }

    /**
     * This runs the $geoWithin query, returning documents with GeoJson fields whose area falls within the given boundary. When determining
     * inclusion, MongoDB considers the border of a shape to be part of the shape, subject to the precision of floating point numbers.
     *
     * @param boundary a polygon describing the boundary to search within.
     * @param crs      the coordinate reference system to use
     * @return T
     * @mongodb.driver.manual reference/operator/query/geoWithin/ $geoWithin
     * @mongodb.server.release 2.4
     * @since 2.0
     */
    T within(com.mongodb.client.model.geojson.Polygon boundary, com.mongodb.client.model.geojson.CoordinateReferenceSystem crs);

    /**
     * This runs the $geoWithin query, returning documents with GeoJson fields whose area falls within the given boundaries. When
     * determining inclusion, MongoDB considers the border of a shape to be part of the shape, subject to the precision of floating point
     * numbers.
     * <p/>
     * These queries are only compatible with MongoDB 2.6 or greater.
     *
     * @param boundaries a multi-polygon describing the areas to search within.
     * @param crs        the coordinate reference system to use
     * @return T
     * @mongodb.driver.manual reference/operator/query/geoWithin/ $geoWithin
     * @mongodb.server.release 2.6
     * @deprecated Use the driver supplied geo types instead
     */
    @Deprecated(since = "2.0", forRemoval = true)
    default T within(MultiPolygon boundaries, CoordinateReferenceSystem crs) {
        return within(boundaries.convert(), crs.convert());
    }

    /**
     * This runs the $geoWithin query, returning documents with GeoJson fields whose area falls within the given boundaries. When
     * determining inclusion, MongoDB considers the border of a shape to be part of the shape, subject to the precision of floating point
     * numbers.
     * <p/>
     * These queries are only compatible with MongoDB 2.6 or greater.
     *
     * @param boundaries a multi-polygon describing the areas to search within.
     * @param crs      the coordinate reference system to use
     * @return T
     * @mongodb.driver.manual reference/operator/query/geoWithin/ $geoWithin
     * @mongodb.server.release 2.6
     * @since 2.0
     */
    T within(com.mongodb.client.model.geojson.MultiPolygon boundaries, com.mongodb.client.model.geojson.CoordinateReferenceSystem crs);
}
