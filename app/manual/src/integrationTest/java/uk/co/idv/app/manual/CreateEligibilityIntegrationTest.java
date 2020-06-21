package uk.co.idv.app.manual;

import org.junit.jupiter.api.Test;
import uk.co.idv.config.manual.ManualConfig;
import uk.co.idv.context.adapter.eligibility.external.ExternalFindIdentityStubConfig;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.channel.gb.GbAs3Mother;
import uk.co.idv.context.entities.channel.gb.GbRsaMother;
import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.entities.phonenumber.MobilePhoneNumberMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;
import uk.co.idv.context.usecases.eligibility.CreateEligibility;
import uk.co.idv.context.usecases.eligibility.CreateEligibilityRequest;
import uk.co.idv.context.usecases.eligibility.CreateEligibilityRequestMother;
import uk.co.idv.context.usecases.identity.find.IdentityNotFoundException;
import uk.co.idv.context.usecases.eligibility.external.data.Delay;
import uk.co.idv.context.usecases.identity.find.FindIdentity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

import java.time.Duration;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class CreateEligibilityIntegrationTest {

    private final ExternalFindIdentityStubConfig stubConfig = ExternalFindIdentityStubConfig.builder()
            .executor(Executors.newFixedThreadPool(2))
            .timeout(Duration.ofMillis(250))
            .phoneNumberDelay(new Delay(350))
            .emailAddressDelay(new Delay(100))
            .build();

    private final ManualConfig appConfig = ManualConfig.builder()
            .stubConfig(stubConfig)
            .build();

    private final CreateEligibility createEligibility = appConfig.createEligibility();
    private final UpdateIdentity updateIdentity = appConfig.updateIdentity();
    private final FindIdentity findIdentity = appConfig.findIdentity();

    @Test
    void shouldThrowExceptionIfIdentityNotFoundForRsa() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.builder()
                .aliases(AliasesMother.creditCardNumberOnly())
                .channel(GbRsaMother.rsa())
                .build();

        IdentityNotFoundException error = catchThrowableOfType(
                () -> createEligibility.create(request),
                IdentityNotFoundException.class
        );

        assertThat(error.getAliases()).isEqualTo(request.getAliases());
    }

    @Test
    void shouldReturnIdentityWithInternalDataIfIdentityExistsForRsa() {
        Aliases aliases = AliasesMother.creditCardNumberOnly();
        Identity identity = IdentityMother.withAliases(aliases);
        Identity created = updateIdentity.update(identity);
        CreateEligibilityRequest request = CreateEligibilityRequestMother.builder()
                .aliases(aliases)
                .channel(GbRsaMother.rsa())
                .build();

        Eligibility eligibility = createEligibility.create(request);

        assertThat(eligibility.getIdentity()).isEqualTo(created);
    }

    @Test
    void shouldCreateIdentityWithExternalDataIfIdentityNotFoundForAs3() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.builder()
                .aliases(AliasesMother.creditCardNumberOnly())
                .channel(GbAs3Mother.as3())
                .build();

        Eligibility eligibility = createEligibility.create(request);

        Identity identity = eligibility.getIdentity();
        assertThat(identity.hasIdvId()).isTrue();
        assertThat(identity.getPhoneNumbers()).isEmpty();
        assertThat(identity.getEmailAddresses()).isNotEmpty();
    }


    @Test
    void shouldAddWithExternalDataToIdentityIfIdentityExistsForAs3() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.builder()
                .aliases(AliasesMother.creditCardNumberOnly())
                .channel(GbAs3Mother.as3())
                .build();

        Eligibility eligibility = createEligibility.create(request);

        Identity identity = eligibility.getIdentity();
        assertThat(identity.hasIdvId()).isTrue();
        assertThat(identity.getPhoneNumbers()).isEmpty();
        assertThat(identity.getEmailAddresses()).isNotEmpty();
    }

    @Test
    void shouldFindIdentityWithExternalDataAfterAs3EligibilityRequest() {
        Aliases aliases = AliasesMother.creditCardNumberOnly();
        Identity identity = IdentityMother.exampleBuilder()
                .aliases(aliases)
                .phoneNumbers(PhoneNumbersMother.with(MobilePhoneNumberMother.withNumber("+447890123456")))
                .emailAddresses(EmailAddressesMother.with("test@email.com"))
                .build();
        Identity existing = updateIdentity.update(identity);
        CreateEligibilityRequest request = CreateEligibilityRequestMother.builder()
                .aliases(aliases)
                .channel(GbAs3Mother.as3())
                .build();
        createEligibility.create(request);

        Identity updated = findIdentity.find(request.getAliases());

        assertThat(updated.getIdvId()).isEqualTo(existing.getIdvId());
        assertThat(updated.getPhoneNumbers()).containsExactlyElementsOf(existing.getPhoneNumbers());
        assertThat(updated.getEmailAddresses()).containsExactlyElementsOf(
                EmailAddressesMother.two().add(existing.getEmailAddresses())
        );
    }

}
