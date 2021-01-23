package uk.co.idv.context.entities.context.sequence.stage;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.eligibility.Eligibility;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class StageIneligibleTest {

    @Test
    void shouldReturnSingularReasonMessageIfJustOneMethodIneligible() {
        Collection<String> names = Collections.singleton("default-method");

        Eligibility ineligible = new StageIneligible(names);

        assertThat(ineligible.getReason()).contains("Stage has ineligible method default-method");
    }

    @Test
    void shouldReturnPluralReasonMessageIfJustOneMethodIneligible() {
        Collection<String> names = Arrays.asList("default-method1", "default-method2");

        Eligibility ineligible = new StageIneligible(names);

        assertThat(ineligible.getReason()).contains("Stage has ineligible methods default-method1, default-method2");
    }

}
