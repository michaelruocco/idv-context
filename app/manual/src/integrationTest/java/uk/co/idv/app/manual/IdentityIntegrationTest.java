package uk.co.idv.app.manual;

import org.junit.jupiter.api.Test;
import uk.co.idv.config.manual.ManualConfig;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.CreditCardNumberMother;
import uk.co.idv.context.entities.alias.DebitCardNumberMother;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;
import uk.co.idv.context.usecases.identity.find.FindIdentity;
import uk.co.idv.context.usecases.identity.merge.MultipleIdentitiesFoundException;
import uk.co.idv.context.usecases.identity.find.IdentityNotFoundException;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class IdentityIntegrationTest {

    private final ManualConfig appConfig = ManualConfig.builder()
            .build();

    private final UpdateIdentity update = appConfig.updateIdentity();
    private final FindIdentity find = appConfig.findIdentity();

    @Test
    void shouldThrowExceptionIfIdentityNotFound() {
        Aliases aliases = AliasesMother.with(IdvIdMother.withValue(UUID.randomUUID()));

        IdentityNotFoundException error = catchThrowableOfType(
                () -> find.find(aliases),
                IdentityNotFoundException.class
        );

        assertThat(error.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldAllocateIdvIdWhenIdentityCreated() {
        Aliases aliases = AliasesMother.creditCardNumberOnly();
        Identity identity = IdentityMother.exampleBuilder()
                .aliases(aliases)
                .emailAddresses(EmailAddressesMother.empty())
                .phoneNumbers(PhoneNumbersMother.empty())
                .build();

        Identity created = update.update(identity);

        assertThat(created.hasIdvId()).isTrue();
    }

    @Test
    void shouldFindCreatedIdentity() {
        Aliases aliases = AliasesMother.creditCardNumberOnly();
        Identity identity = IdentityMother.exampleBuilder()
                .aliases(aliases)
                .emailAddresses(EmailAddressesMother.empty())
                .phoneNumbers(PhoneNumbersMother.empty())
                .build();
        Identity created = update.update(identity);

        Identity found = find.find(aliases);

        assertThat(found).isEqualTo(created);
    }

    @Test
    void shouldThrowExceptionOnUpdateIfMultipleIdentitiesExist() {
        Alias creditCardNumber = CreditCardNumberMother.creditCardNumber();
        Identity creditIdentity = update.update(IdentityMother.withAliases(creditCardNumber));
        Alias debitCardNumber = DebitCardNumberMother.debitCardNumber();
        Identity debitIdentity = update.update(IdentityMother.withAliases(debitCardNumber));

        MultipleIdentitiesFoundException error = catchThrowableOfType(
                () -> update.update(IdentityMother.withAliases(creditCardNumber, debitCardNumber)),
                MultipleIdentitiesFoundException.class
        );

        assertThat(error.getAliases()).containsExactly(
                creditCardNumber,
                debitCardNumber
        );
        assertThat(error.getExistingIdentities()).containsExactly(creditIdentity, debitIdentity);
    }

}
