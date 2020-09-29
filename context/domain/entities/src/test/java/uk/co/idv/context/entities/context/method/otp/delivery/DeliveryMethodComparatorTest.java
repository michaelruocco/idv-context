package uk.co.idv.context.entities.context.method.otp.delivery;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DeliveryMethodComparatorTest {

    private final DeliveryMethodComparator comparator = new DeliveryMethodComparator();

    @Test
    void shouldOrderByMostRecentFirst() {
        DeliveryMethod notUpdated = DeliveryMethodMother.withoutLastUpdated();
        DeliveryMethod mid = DeliveryMethodMother.withLastUpdated(Instant.parse("2020-08-28T08:08:08.008Z"));
        DeliveryMethod leastRecent = DeliveryMethodMother.withLastUpdated(Instant.parse("2020-07-27T07:07:07.007Z"));
        DeliveryMethod mostRecent = DeliveryMethodMother.withLastUpdated(Instant.parse("2020-09-29T09:09:09.009Z"));
        List<DeliveryMethod> methods = Arrays.asList(
                notUpdated,
                mid,
                leastRecent,
                mostRecent
        );

        methods.sort(comparator);

        assertThat(methods).containsExactly(
                mostRecent,
                mid,
                leastRecent,
                notUpdated
        );
    }

    @Test
    void shouldOrderValueIfLastUpdatedNotPresent() {
        DeliveryMethod cMethod = DeliveryMethodMother.builder().value("c").lastUpdated(null).build();
        DeliveryMethod bMethod = DeliveryMethodMother.builder().value("b").lastUpdated(null).build();
        DeliveryMethod aMethod = DeliveryMethodMother.builder().value("a").lastUpdated(null).build();
        List<DeliveryMethod> methods = Arrays.asList(
                cMethod,
                bMethod,
                aMethod
        );

        methods.sort(comparator);

        assertThat(methods).containsExactly(
                aMethod,
                bMethod,
                cMethod
        );
    }

    @Test
    void shouldSortByLastUpdatedBeforeValue() {
        DeliveryMethod zMethod = DeliveryMethodMother.builder().value("z").lastUpdated(Instant.now()).build();
        DeliveryMethod aMethod = DeliveryMethodMother.builder().value("a").lastUpdated(null).build();
        List<DeliveryMethod> methods = Arrays.asList(
                zMethod,
                aMethod
        );

        methods.sort(comparator);

        assertThat(methods).containsExactly(
                zMethod,
                aMethod
        );
    }

}
