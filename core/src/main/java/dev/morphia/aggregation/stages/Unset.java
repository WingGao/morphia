package dev.morphia.aggregation.stages;

import dev.morphia.aggregation.expressions.Expressions;
import dev.morphia.aggregation.expressions.impls.Expression;
import dev.morphia.annotations.internal.MorphiaInternal;
import dev.morphia.sofia.Sofia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Removes/excludes fields from documents.
 *
 * @aggregation.expression $unset
 */
public class Unset extends Stage {
    private static final Logger LOG = LoggerFactory.getLogger(Unset.class);

    private final List<Expression> fields = new ArrayList<>();

    protected Unset() {
        super("$unset");
    }

    /**
     * Creates a new stage with the given fields
     *
     * @param name  the first field
     * @param names the others
     * @return this
     * @deprecated use {@link #unset(String, String...)}
     */
    @Deprecated(forRemoval = true)
    public static Unset fields(String name, String... names) {
        Unset unset = new Unset()
                .add(name);
        for (String additional : names) {
            unset.add(additional);
        }
        return unset;
    }

    /**
     * Creates a new stage with the given fields
     *
     * @param name  the first field
     * @param names the others
     * @return this
     * @since 2.2
     */
    public static Unset unset(String name, String... names) {
        Unset unset = new Unset()
                .add(name);
        for (String additional : names) {
            unset.add(additional);
        }
        return unset;
    }

    /**
     * @return the fields
     * @morphia.internal
     */
    @MorphiaInternal
    public List<Expression> getFields() {
        return fields;
    }

    private Unset add(String name) {
        String fieldName = name;
        if (fieldName.startsWith("$")) {
            fieldName = fieldName.substring(1);
            LOG.warn(Sofia.unsetNamesDollarSign());
        }
        fields.add(Expressions.value(fieldName));
        return this;
    }
}
