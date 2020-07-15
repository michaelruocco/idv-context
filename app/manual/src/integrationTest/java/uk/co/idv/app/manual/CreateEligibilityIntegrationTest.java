package uk.co.idv.app.manual;

import org.junit.jupiter.api.Test;
import uk.co.idv.config.identity.IdentityConfig;
import uk.co.idv.config.repository.RepositoryConfig;
import uk.co.idv.config.repository.inmemory.InMemoryRepositoryConfig;
import uk.co.idv.context.adapter.eligibility.external.ExternalFindIdentityStubConfig;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.channel.gb.As3Mother;
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
import uk.co.idv.context.usecases.identity.IdentityFacade;
import uk.co.idv.context.usecases.identity.find.IdentityNotFoundException;

import java.time.Duration;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class CreateEligibilityIntegrationTest {

    private final RepositoryConfig repositoryConfig = new InMemoryRepositoryConfig();
    private final ExternalFindIdentityStubConfig stubConfig = ExternalFindIdentityStubConfig.builder()
            .executor(Executors.newFixedThreadPool(2))
            .timeout(Duration.ofMillis(250))
            .phoneNumberDelay(Duration.ofMillis(400))
            .emailAddressDelay(Duration.ofMillis(100))
            .build();

    private final IdentityConfig identityConfig = IdentityConfig.builder()
            .repository(repositoryConfig.identityRepository())
            .stubConfig(stubConfig)
            .build();

    private final CreateEligibility createEligibility = identityConfig.createEligibility();
    private final IdentityFacade facade = identityConfig.identityFacade();

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
        Identity created = facade.update(identity);
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
                .channel(As3Mother.as3())
                .build();

        Eligibility eligibility = createEligibility.create(request);

        Identity identity = eligibility.getIdentity();
        assertThat(identity.hasIdvId()).isTrue();
        assertThat(identity.getPhoneNumbers()).isEmpty();
        assertThat(identity.getEmailAddresses()).isNotEmpty();
    }


    @Test
    void shouldAddExternalDataToIdentityIfIdentityExistsForAs3() {
        Aliases aliases = AliasesMother.creditCardNumberOnly();
        Identity identity = IdentityMother.exampleBuilder()
                .aliases(aliases)
                .phoneNumbers(PhoneNumbersMother.with(MobilePhoneNumberMother.withNumber("+447890123456")))
                .emailAddresses(EmailAddressesMother.with("test@email.com"))
                .build();
        Identity existing = facade.update(identity);
        CreateEligibilityRequest request = CreateEligibilityRequestMother.builder()
                .aliases(aliases)
                .channel(As3Mother.as3())
                .build();
        createEligibility.create(request);

        Identity updated = facade.find(request.getAliases());

        assertThat(updated.getIdvId()).isEqualTo(existing.getIdvId());
        assertThat(updated.getPhoneNumbers()).containsExactlyElementsOf(existing.getPhoneNumbers());
        assertThat(updated.getEmailAddresses()).containsExactlyElementsOf(
                EmailAddressesMother.two().add(existing.getEmailAddresses())
        );
    }

}
