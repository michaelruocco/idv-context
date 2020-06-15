package uk.co.idv.context.adapter.identity.service.find;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;
import uk.co.idv.context.usecases.identity.request.ExternalFindIdentityRequest;
import uk.co.idv.context.usecases.identity.service.find.ExternalFindIdentity;
import uk.co.idv.context.usecases.identity.request.ExternalFindIdentityRequestMother;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

public class StubFindIdentityIntegrationTest {

    private final ExecutorService executor = Executors.newFixedThreadPool(1);
    private final StubFindIdentityConfig config = StubFindIdentityConfig.build(executor);
    private final ExternalFindIdentity find = StubFindIdentity.build(config);

    @Test
    void shouldFindStubbedIdentityWithCountryCode() {
        ExternalFindIdentityRequest request = ExternalFindIdentityRequestMother.build();

        Identity identity = find.find(request);

        assertThat(identity.getCountry()).isEqualTo(request.getCountry());
    }

    @Test
    void shouldFindStubbedIdentityWithAliases() {
        ExternalFindIdentityRequest request = ExternalFindIdentityRequestMother.build();

        Identity identity = find.find(request);

        Aliases expectedAliases = AliasesMother.idvIdAndCreditCardNumber();
        assertThat(identity.getAliases()).isEqualTo(expectedAliases.add(request.getAliases()));
    }

    @Test
    void shouldFindStubbedIdentityWithPhoneNumbers() {
        ExternalFindIdentityRequest request = ExternalFindIdentityRequestMother.build();

        Identity identity = find.find(request);

        assertThat(identity.getPhoneNumbers()).isEqualTo(PhoneNumbersMother.mobileAndOther());
    }

    @Test
    void shouldFindStubbedIdentityWithEmailAddresses() {
        ExternalFindIdentityRequest request = ExternalFindIdentityRequestMother.build();

        Identity identity = find.find(request);

        assertThat(identity.getEmailAddresses()).containsExactlyElementsOf(EmailAddressesMother.two());
    }

}
