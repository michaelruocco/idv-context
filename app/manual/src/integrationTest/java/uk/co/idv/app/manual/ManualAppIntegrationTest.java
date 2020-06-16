package uk.co.idv.app.manual;

import org.junit.jupiter.api.Test;
import uk.co.idv.config.manual.ManualConfig;
import uk.co.idv.context.adapter.identity.find.external.StubFindIdentityConfig;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.channel.gb.GbAs3Mother;
import uk.co.idv.context.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;
import uk.co.idv.context.usecases.identity.IdentityNotFoundException;
import uk.co.idv.context.usecases.identity.request.FindIdentityRequestMother;
import uk.co.idv.context.usecases.identity.request.UpdateIdentityRequestMother;
import uk.co.idv.context.usecases.identity.find.FindIdentity;
import uk.co.idv.context.usecases.identity.find.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.find.data.Delay;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentityRequest;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class ManualAppIntegrationTest {

    private final StubFindIdentityConfig stubConfig = StubFindIdentityConfig.builder()
            .executor(Executors.newFixedThreadPool(2))
            .timeout(Duration.ofMillis(250))
            .phoneNumberDelay(new Delay(300))
            .emailAddressDelay(new Delay(100))
            .build();

    private final ManualConfig appConfig = ManualConfig.builder()
            .stubConfig(stubConfig)
            .build();

    @Test
    void shouldCreateIdentity() {
        Aliases aliases = AliasesMother.creditCardNumberOnly();
        Identity identity = IdentityMother.exampleBuilder()
                .aliases(aliases)
                .emailAddresses(EmailAddressesMother.empty())
                .phoneNumbers(PhoneNumbersMother.empty())
                .build();

        UpdateIdentityRequest request = as3UpdateRequest(identity);
        UpdateIdentity update = appConfig.updateIdentity();

        Identity created = update.update(request);

        assertThat(created.hasIdvId()).isTrue();
        assertThat(created.getPhoneNumbers()).isEmpty();
        assertThat(created.getEmailAddresses()).isNotEmpty();
    }

    @Test
    void shouldFindCreatedIdentity() {
        Aliases aliases = AliasesMother.creditCardNumberOnly();
        Identity identity = IdentityMother.exampleBuilder()
                .aliases(aliases)
                .emailAddresses(EmailAddressesMother.empty())
                .phoneNumbers(PhoneNumbersMother.empty())
                .build();

        UpdateIdentityRequest request = as3UpdateRequest(identity);
        UpdateIdentity update = appConfig.updateIdentity();
        Identity created = update.update(request);

        FindIdentity find = appConfig.findIdentity();
        FindIdentityRequest findRequest = FindIdentityRequestMother.withAliases(aliases);

        Identity found = find.find(findRequest);

        assertThat(found).isEqualTo(created);
    }

    @Test
    void shouldThrowExceptionIfIdentityNotFound() {
        Aliases aliases = AliasesMother.with(IdvIdMother.withValue(UUID.randomUUID()));
        FindIdentityRequest findRequest = FindIdentityRequestMother.withAliases(aliases);
        FindIdentity find = appConfig.findIdentity();

        IdentityNotFoundException error = catchThrowableOfType(
                () -> find.find(findRequest),
                IdentityNotFoundException.class
        );

        assertThat(error.getAliases()).isEqualTo(aliases);
    }

    private UpdateIdentityRequest as3UpdateRequest(Identity identity) {
        return UpdateIdentityRequestMother.builder()
                .channel(GbAs3Mother.as3())
                .identity(identity)
                .build();
    }

}
