package uk.co.idv.identity.usecases.identity.create;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class IdentityValidatorTest {

    private final IdentityValidator validator = new IdentityValidator();

    @Test
    void shouldReturnTrueIfIdentityHasCountry() {
        Identity identity = IdentityMother.withCountry(CountryCode.GB);

        boolean valid = validator.validate(identity);

        assertThat(valid).isTrue();
    }

    @Test
    void shouldThrowExceptionIfIdentityDoesNotHaveCountry() {
        Identity identity = IdentityMother.withoutCountry();

        Throwable error = catchThrowable(() -> validator.validate(identity));

        assertThat(error).isInstanceOf(CountryNotProvidedException.class);
    }

}
