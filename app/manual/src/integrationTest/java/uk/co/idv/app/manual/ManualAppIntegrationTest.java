package uk.co.idv.app.manual;

import org.junit.jupiter.api.Test;
import uk.co.idv.config.manual.ManualConfig;
import uk.co.idv.context.adapter.identity.find.external.StubFindIdentityConfig;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.CreditCardNumberMother;
import uk.co.idv.context.entities.alias.DebitCardNumberMother;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.entities.channel.gb.GbAs3Mother;
import uk.co.idv.context.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;
import uk.co.idv.context.usecases.identity.merge.MultipleIdentitiesFoundException;
import uk.co.idv.context.usecases.identity.update.ChannelNotConfiguredForIdentityUpdateException;
import uk.co.idv.context.usecases.identity.find.IdentityNotFoundException;
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
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class ManualAppIntegrationTest {

    private final StubFindIdentityConfig stubConfig = StubFindIdentityConfig.builder()
            .executor(Executors.newFixedThreadPool(6))
            .timeout(Duration.ofMillis(250))
            .phoneNumberDelay(new Delay(300))
            .emailAddressDelay(new Delay(100))
            .build();

    private final ManualConfig appConfig = ManualConfig.builder()
            .stubConfig(stubConfig)
            .build();

    private final UpdateIdentity update = appConfig.updateIdentity();
    private final FindIdentity find = appConfig.findIdentity();

    @Test
    void shouldCreateIdentity() {
        Aliases aliases = AliasesMother.creditCardNumberOnly();
        Identity identity = IdentityMother.exampleBuilder()
                .aliases(aliases)
                .emailAddresses(EmailAddressesMother.empty())
                .phoneNumbers(PhoneNumbersMother.empty())
                .build();
        UpdateIdentityRequest request = as3UpdateRequest(identity);

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
        Identity created = update.update(request);
        FindIdentityRequest findRequest = FindIdentityRequestMother.withAliases(aliases);

        Identity found = find.find(findRequest);

        assertThat(found).isEqualTo(created);
    }

    @Test
    void shouldThrowExceptionOnUpdateIfMultipleIdentitiesExist() {
        Alias creditCardNumber = CreditCardNumberMother.creditCardNumber();
        Identity creditIdentity = update.update(as3UpdateRequest((IdentityMother.withAliases(creditCardNumber))));
        Alias debitCardNumber = DebitCardNumberMother.debitCardNumber();
        Identity debitIdentity = update.update(as3UpdateRequest((IdentityMother.withAliases(debitCardNumber))));
        UpdateIdentityRequest request = as3UpdateRequest(IdentityMother.withAliases(creditCardNumber, debitCardNumber));

        MultipleIdentitiesFoundException error = catchThrowableOfType(
                () -> update.update(request),
                MultipleIdentitiesFoundException.class
        );

        assertThat(error.getAliases()).containsExactly(
                creditCardNumber,
                debitCardNumber,
                DebitCardNumberMother.debitCardNumber1()
        );
        assertThat(error.getExistingIdentities()).containsExactly(creditIdentity, debitIdentity);
    }

    @Test
    void shouldThrowExceptionIfChannelNotConfiguredForIdentityUpdate() {
        Channel channel = DefaultChannelMother.build();
        UpdateIdentityRequest request = UpdateIdentityRequestMother.withChannel(channel);

        Throwable error = catchThrowable(() -> update.update(request));

        assertThat(error)
                .isInstanceOf(ChannelNotConfiguredForIdentityUpdateException.class)
                .hasMessage(channel.getId());
    }

    @Test
    void shouldThrowExceptionIfIdentityNotFound() {
        Aliases aliases = AliasesMother.with(IdvIdMother.withValue(UUID.randomUUID()));
        FindIdentityRequest findRequest = FindIdentityRequestMother.withAliases(aliases);

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
