package uk.co.idv.app.plain.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.app.plain.Application;
import uk.co.idv.app.plain.TestHarness;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.alias.DefaultAliases;
import uk.co.idv.identity.entities.channel.DefaultChannel;
import uk.co.idv.identity.entities.channel.DefaultChannelMother;
import uk.co.idv.identity.entities.channel.gb.AbcMother;
import uk.co.idv.identity.entities.channel.gb.GbRsaMother;
import uk.co.idv.identity.entities.eligibility.IdentityEligibility;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.entities.identity.IdentityNotFoundException;
import uk.co.idv.identity.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;
import uk.co.idv.identity.entities.eligibility.CreateEligibilityRequest;
import uk.co.idv.identity.entities.eligibility.CreateEligibilityRequestMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class CreateEligibilityIntegrationTest {

    private final TestHarness harness = new TestHarness();
    private final Application application = harness.getApplication();

    @Test
    void shouldThrowExceptionIfIdentityNotFoundForRsa() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.builder()
                .aliases(AliasesMother.creditCardNumberOnly())
                .channel(GbRsaMother.rsa())
                .build();

        IdentityNotFoundException error = catchThrowableOfType(
                () -> application.create(request),
                IdentityNotFoundException.class
        );

        assertThat(error.getAliases()).isEqualTo(request.getAliases());
    }

    @Test
    void shouldReturnIdentityWithInternalDataIfIdentityExistsForRsa() {
        DefaultAliases aliases = AliasesMother.creditCardNumberOnly();
        Identity identity = IdentityMother.withAliases(aliases);
        Identity created = application.update(identity);
        CreateEligibilityRequest request = CreateEligibilityRequestMother.builder()
                .aliases(aliases)
                .channel(GbRsaMother.rsa())
                .build();

        IdentityEligibility eligibility = application.create(request);

        assertThat(eligibility.getIdentity()).usingRecursiveComparison().isEqualTo(created);
    }

    @Test
    void shouldCreateIdentityWithExternalDataIfIdentityNotFoundForAbc() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.builder()
                .aliases(AliasesMother.creditCardNumberOnly())
                .channel(AbcMother.abc())
                .build();

        IdentityEligibility eligibility = application.create(request);

        Identity identity = eligibility.getIdentity();
        assertThat(identity.hasIdvId()).isTrue();
        assertThat(identity.getPhoneNumbers()).isEmpty();
        assertThat(identity.getEmailAddresses()).isNotEmpty();
    }

    @Test
    void shouldAddExternalDataToIdentityIfIdentityExistsForAbc() {
        DefaultAliases aliases = AliasesMother.creditCardNumberOnly();
        Identity identity = IdentityMother.exampleBuilder()
                .aliases(aliases)
                .phoneNumbers(PhoneNumbersMother.with(PhoneNumberMother.withNumber("+447890123456")))
                .emailAddresses(EmailAddressesMother.with("test@email.com"))
                .build();
        Identity existing = application.update(identity);
        CreateEligibilityRequest request = CreateEligibilityRequestMother.builder()
                .aliases(aliases)
                .channel(AbcMother.abc())
                .build();
        application.create(request);

        Identity updated = application.findIdentity(request.getAliases());

        assertThat(updated.getIdvId()).isEqualTo(existing.getIdvId());
        assertThat(updated.getPhoneNumbers()).containsExactlyElementsOf(existing.getPhoneNumbers());
        assertThat(updated.getEmailAddresses()).containsExactlyElementsOf(
                EmailAddressesMother.two().add(existing.getEmailAddresses())
        );
    }

    @Test
    void shouldAppendProvidedChannelDataToIdentity() {
        Aliases aliases = AliasesMother.creditCardNumberOnly();
        Identity identity = IdentityMother.exampleBuilder()
                .aliases(aliases)
                .phoneNumbers(PhoneNumbersMother.empty())
                .emailAddresses(EmailAddressesMother.empty())
                .build();
        application.update(identity);
        DefaultChannel channel = DefaultChannelMother.build();
        CreateEligibilityRequest request = CreateEligibilityRequestMother.builder()
                .aliases(aliases)
                .channel(channel)
                .build();

        IdentityEligibility eligibility = application.create(request);

        Identity updated = eligibility.getIdentity();
        assertThat(updated)
                .usingRecursiveComparison()
                .ignoringFields("phoneNumbers", "emailAddresses", "aliases")
                .isEqualTo(identity);
        assertThat(updated.getAliases()).containsAll(aliases);
        assertThat(updated.getPhoneNumbers()).containsExactlyElementsOf(channel.getPhoneNumbers());
        assertThat(updated.getEmailAddresses()).containsExactlyElementsOf(channel.getEmailAddresses());
    }

}
