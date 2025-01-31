package dev.morphia.test.aggregation.stages;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.test.aggregation.AggregationTest;
import dev.morphia.test.aggregation.model.Artwork;
import dev.morphia.test.aggregation.model.Book;
import org.bson.Document;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;

import static dev.morphia.aggregation.expressions.AccumulatorExpressions.push;
import static dev.morphia.aggregation.expressions.AccumulatorExpressions.sum;
import static dev.morphia.aggregation.expressions.Expressions.field;
import static dev.morphia.aggregation.expressions.Expressions.value;
import static dev.morphia.aggregation.stages.Bucket.bucket;
import static java.lang.Integer.valueOf;
import static java.util.Arrays.asList;
import static org.bson.Document.parse;
import static org.testng.Assert.assertEquals;

public class TestBucket extends AggregationTest {
    @Test
    public void testBucket() {
        List<Document> list = List.of(
                parse("{'_id': 1, 'title': 'The Pillars of Society', 'artist': 'Grosz', 'year': 1926, 'price': NumberDecimal('199.99') }"),
                parse("{'_id': 2, 'title': 'Melancholy III', 'artist': 'Munch', 'year': 1902, 'price': NumberDecimal('280.00') }"),
                parse("{'_id': 3, 'title': 'Dancer', 'artist': 'Miro', 'year': 1925, 'price': NumberDecimal('76.04') }"),
                parse("{'_id': 4, 'title': 'The Great Wave off Kanagawa', 'artist': 'Hokusai', 'price': NumberDecimal('167.30') }"),
                parse("{'_id': 5, 'title': 'The Persistence of Memory', 'artist': 'Dali', 'year': 1931, 'price': NumberDecimal('483.00') }"),
                parse("{'_id': 6, 'title': 'Composition VII', 'artist': 'Kandinsky', 'year': 1913, 'price': NumberDecimal('385.00') }"),
                parse("{'_id': 7, 'title': 'The Scream', 'artist': 'Munch', 'year': 1893}"),
                parse("{'_id': 8, 'title': 'Blue Flower', 'artist': 'O\\'Keefe', 'year': 1918, 'price': NumberDecimal('118.42') }"));

        insert("artwork", list);

        List<Document> results = getDs().aggregate(Artwork.class)
                .bucket(bucket()
                        .groupBy(field("price"))
                        .boundaries(value(0), value(200), value(400))
                        .defaultValue("Other")
                        .outputField("count", sum(value(1)))
                        .outputField("titles", push().single(field("title"))))
                .execute(Document.class)
                .toList();

        List<Document> documents = List.of(
                parse("{'_id': 0, 'count': 4, 'titles': ['The Pillars of Society', 'Dancer', 'The Great Wave off Kanagawa', 'Blue Flower']}"),
                parse("{'_id': 200, 'count': 2, 'titles': ['Melancholy III', 'Composition VII']}"),
                parse("{'_id': 'Other', 'count': 2, 'titles': ['The Persistence of Memory', 'The Scream']}"));
        assertEquals(results, documents);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testBucketWithBoundariesWithSizeLessThanTwo() {
        homer();

        getDs().aggregate(Book.class)
                .bucket(bucket()
                        .groupBy(field("copies"))
                        .boundaries(value(10))
                        .outputField("count", sum(value(1))))
                .execute(BucketResult.class);
    }

    private void homer() {
        getDs().save(asList(new Book("The Banquet", "Dante", 2),
                new Book("Divine Comedy", "Dante", 1),
                new Book("Eclogues", "Dante", 2),
                new Book("The Odyssey", "Homer", 10),
                new Book("Iliad", "Homer", 10)));
    }

    @Test
    public void testBucketWithOptions() {
        homer();

        Iterator<BucketResult> aggregate = getDs().aggregate(Book.class)
                .bucket(bucket()
                        .groupBy(field("copies"))
                        .boundaries(value(1), value(5), value(10))
                        .defaultValue(-1)
                        .outputField("count", sum(value(1))))
                .execute(BucketResult.class);

        BucketResult result2 = aggregate.next();
        Assert.assertEquals(result2.getId(), valueOf(-1));
        Assert.assertEquals(result2.getCount(), 2);

        BucketResult result1 = aggregate.next();
        Assert.assertEquals(result1.getId(), valueOf(1));
        Assert.assertEquals(result1.getCount(), 3);

    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testBucketWithUnsortedBoundaries() {
        homer();

        Iterator<BucketResult> aggregate = getDs().aggregate(Book.class)
                .bucket(bucket()
                        .groupBy(field("copies"))
                        .boundaries(value(5), value(1), value(10))
                        .defaultValue("test")
                        .outputField("count", sum(value(1))))
                .execute(BucketResult.class);
    }

    @Test
    public void testBucketWithoutOptions() {
        homer();

        Iterator<BucketResult> aggregate = getDs().aggregate(Book.class)
                .bucket(bucket()
                        .groupBy(field("copies"))
                        .boundaries(value(1), value(5), value(12)))
                .execute(BucketResult.class);
        BucketResult result1 = aggregate.next();
        Assert.assertEquals(result1.getId(), 1);
        Assert.assertEquals(result1.getCount(), 3);

        BucketResult result2 = aggregate.next();
        Assert.assertEquals(result2.getId(), valueOf(5));
        Assert.assertEquals(result2.getCount(), 2);
    }

    @Entity
    private static class BucketResult {
        @Id
        private Integer id;
        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "BucketResult{"
                    + "id="
                    + id
                    + ", count=" + count
                    + '}';
        }
    }
}
