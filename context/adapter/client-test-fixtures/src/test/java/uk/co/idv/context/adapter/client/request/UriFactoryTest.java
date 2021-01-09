package uk.co.idv.context.adapter.client.request;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class UriFactoryTest {

    @Test
    void shouldThrowExceptionIfUriIsInvalid() {
        String invalid = "::invalid";

        Throwable error = catchThrowable(() -> UriFactory.toUri(invalid));

        assertThat(error)
                .isInstanceOf(InvalidUriException.class)
                .hasCauseInstanceOf(URISyntaxException.class);
    }

}
