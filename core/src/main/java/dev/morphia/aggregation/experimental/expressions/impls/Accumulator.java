package dev.morphia.aggregation.experimental.expressions.impls;

import dev.morphia.Datastore;
import org.bson.BsonWriter;
import org.bson.codecs.EncoderContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static dev.morphia.aggregation.experimental.codecs.ExpressionHelper.document;
import static dev.morphia.aggregation.experimental.codecs.ExpressionHelper.expression;

/**
 * Base class for the accumulator expression types.
 *
 * @since 2.0
 */
public class Accumulator extends Expression {

    /**
     * @param operation
     * @param values
     * @morphia.internal
     */
    public Accumulator(String operation, List<Expression> values) {
        super(operation, values);
    }

    @Override
    public void encode(Datastore datastore, BsonWriter writer, EncoderContext encoderContext) {
        document(writer, () -> {
            writer.writeName(getOperation());
            if (getValue().size() > 1) {
                writer.writeStartArray();
            }
            for (Expression expression : getValue()) {
                expression(datastore, writer, expression, encoderContext);
            }
            if (getValue().size() > 1) {
                writer.writeEndArray();
            }
        });
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public List<Expression> getValue() {
        return (List<Expression>) super.getValue();
    }
}
