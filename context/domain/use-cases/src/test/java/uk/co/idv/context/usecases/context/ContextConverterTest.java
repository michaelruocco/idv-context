package uk.co.idv.context.usecases.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.ApiContext;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;

import static org.assertj.core.api.Assertions.assertThat;

class ContextConverterTest {

    private final ContextConverter converter = new ContextConverter();

    @Test
    void shouldPopulateId() {
        Context context = ContextMother.build();

        ApiContext apiContext = converter.toApiContext(context);

        assertThat(context.getId()).isEqualTo(apiContext.getId());
    }

    @Test
    void shouldPopulateCreated() {
        Context context = ContextMother.build();

        ApiContext apiContext = converter.toApiContext(context);

        assertThat(context.getCreated()).isEqualTo(apiContext.getCreated());
    }

    @Test
    void shouldPopulateExpiry() {
        Context context = ContextMother.build();

        ApiContext apiContext = converter.toApiContext(context);

        assertThat(context.getExpiry()).isEqualTo(apiContext.getExpiry());
    }

    @Test
    void shouldPopulateChannel() {
        Context context = ContextMother.build();

        ApiContext apiContext = converter.toApiContext(context);

        assertThat(context.getChannel()).isEqualTo(apiContext.getChannel());
    }

    @Test
    void shouldPopulateActivity() {
        Context context = ContextMother.build();

        ApiContext apiContext = converter.toApiContext(context);

        assertThat(context.getActivity()).isEqualTo(apiContext.getActivity());
    }

    @Test
    void shouldPopulateAliases() {
        Context context = ContextMother.build();

        ApiContext apiContext = converter.toApiContext(context);

        assertThat(context.getAliases()).isEqualTo(apiContext.getAliases());
    }

    @Test
    void shouldPopulateSequences() {
        Context context = ContextMother.build();

        ApiContext apiContext = converter.toApiContext(context);

        assertThat(context.getSequences()).isEqualTo(apiContext.getSequences());
    }

    @Test
    void shouldPopulateVerifications() {
        Context context = ContextMother.build();

        ApiContext apiContext = converter.toApiContext(context);

        assertThat(context.getVerifications()).isEqualTo(apiContext.getVerifications());
    }

}
