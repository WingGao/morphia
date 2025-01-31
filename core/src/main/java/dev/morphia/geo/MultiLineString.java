package dev.morphia.geo;

import com.mongodb.lang.Nullable;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a series of lines, which will saved into MongoDB as per the <a href="http://geojson.org/geojson-spec
 * .html#id6">GeoJSON
 * specification</a>.
 * <p/>
 * The factory for creating a MultiLineString is the {@code GeoJson.multiLineString} method.
 *
 * @see dev.morphia.geo.GeoJson#multiLineString(LineString...)
 * @deprecated use the driver-provided types instead
 */
@SuppressWarnings("removal")
@Deprecated(since = "2.0", forRemoval = true)
public class MultiLineString implements Geometry {
    @Id
    private ObjectId id;
    private final List<LineString> coordinates;

    @SuppressWarnings("UnusedDeclaration") // needed for Morphia
    private MultiLineString() {
        this.coordinates = new ArrayList<>();
    }

    MultiLineString(LineString... lineStrings) {
        coordinates = Arrays.asList(lineStrings);
    }

    MultiLineString(List<LineString> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public List<LineString> getCoordinates() {
        return coordinates;
    }

    @Override
    public int hashCode() {
        return coordinates.hashCode();
    }

    /* equals, hashCode and toString. Useful primarily for testing and debugging. Don't forget to re-create when changing this class */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MultiLineString that = (MultiLineString) o;

        return coordinates.equals(that.coordinates);
    }

    @Override
    public String toString() {
        return "MultiLineString{"
                + "coordinates=" + coordinates
                + '}';
    }

    @Override
    public com.mongodb.client.model.geojson.MultiLineString convert() {
        return convert(null);
    }

    @Override
    public com.mongodb.client.model.geojson.MultiLineString convert(@Nullable CoordinateReferenceSystem crs) {
        return new com.mongodb.client.model.geojson.MultiLineString(crs != null ? crs.convert() : null,
                GeoJson.convertLineStrings(coordinates));
    }
}
