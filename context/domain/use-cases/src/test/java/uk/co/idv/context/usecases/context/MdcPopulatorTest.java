package uk.co.idv.context.usecases.context;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class MdcPopulatorTest {

    private final MdcPopulator populator = new MdcPopulator();

    @BeforeEach
    @AfterEach
    void clearMdc() {
        MDC.clear();
    }

    @Test
    void shouldPopulateContextIdInMdcContext() {
        UUID id = UUID.randomUUID();

        populator.populateContextId(id);

        assertThat(MDC.getCopyOfContextMap()).contains(
                entry("context-id", id.toString())
        );
    }

}
