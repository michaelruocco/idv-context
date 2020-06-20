package uk.co.idv.context.adapter.eligibility.external;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.DebitCardNumberMother;
import uk.co.idv.context.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;
import uk.co.idv.context.usecases.eligibility.CreateEligibilityRequestMother;
import uk.co.idv.context.usecases.eligibility.external.ExternalFindIdentity;
import uk.co.idv.context.usecases.eligibility.external.ExternalFindIdentityRequest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

public class ExternalFindIdentityStubIntegrationTest {

    private final ExecutorService executor = Executors.newFixedThreadPool(1);
    private final ExternalFindIdentityStubConfig config = ExternalFindIdentityStubConfig.build(executor);
    private final ExternalFindIdentity find = ExternalFindIdentityStub.build(config);

    @Test
    void shouldFindStubbedIdentityWithCountryCode() {
        ExternalFindIdentityRequest request = CreateEligibilityRequestMother.build();

        Identity identity = find.find(request);

        assertThat(identity.getCountry()).isEqualTo(request.getCountry());
    }

    @Test
    void shouldFindStubbedIdentityWithAliases() {
        ExternalFindIdentityRequest request = CreateEligibilityRequestMother.build();

        Identity identity = find.find(request);

        Aliases expectedAliases = AliasesMother.with(DebitCardNumberMother.debitCardNumber1());
        assertThat(identity.getAliases()).isEqualTo(expectedAliases.add(request.getAliases()));
    }

    @Test
    void shouldFindStubbedIdentityWithPhoneNumbers() {
        ExternalFindIdentityRequest request = CreateEligibilityRequestMother.build();

        Identity identity = find.find(request);

        assertThat(identity.getPhoneNumbers()).isEqualTo(PhoneNumbersMother.mobileAndOther());
    }

    @Test
    void shouldFindStubbedIdentityWithEmailAddresses() {
        ExternalFindIdentityRequest request = CreateEligibilityRequestMother.build();

        Identity identity = find.find(request);

        assertThat(identity.getEmailAddresses()).containsExactlyElementsOf(EmailAddressesMother.two());
    }

}
