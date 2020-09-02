package uk.co.idv.identity.adapter.json.error.country.mismatch;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.adapter.json.error.ApiError;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class CountryMismatchErrorTest {

    private final CountryCode existing = CountryCode.GB;
    private final CountryCode updated = CountryCode.DE;

    private final ApiError error = CountryMismatchError.builder()
            .existing(existing)
            .updated(updated)
            .build();

    @Test
    void shouldReturnStatus() {
        assertThat(error.getStatus()).isEqualTo(422);
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Cannot merge identities if countries do not match");
    }

    @Test
    void shouldReturnMessage() {
        String expectedMessage = String.format(
                "attempted to merge identity from %s to %s",
                existing,
                updated
        );

        assertThat(error.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldReturnMeta() {
        assertThat(error.getMeta()).contains(
                entry("existing", existing),
                entry("new", updated)
        );
    }

}
