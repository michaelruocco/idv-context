package uk.co.idv.context.adapter.stub.identity.find;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.find.FindIdentity;
import uk.co.idv.context.usecases.identity.FindIdentityRequestMother;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

public class StubFindIdentityIntegrationTest {

    private final ExecutorService executor = Executors.newFixedThreadPool(1);
    private final StubFindIdentityConfig config = StubFindIdentityConfig.build(executor);
    private final FindIdentity finder = new StubFindIdentity(config);

    @Test
    void shouldFindStubbedIdentityWithCountryCode() {
        FindIdentityRequest request = FindIdentityRequestMother.build();

        Identity identity = finder.find(request);

        assertThat(identity.getCountry()).isEqualTo(request.getCountry());
    }

    @Test
    void shouldFindStubbedIdentityWithAliases() {
        FindIdentityRequest request = FindIdentityRequestMother.build();

        Identity identity = finder.find(request);

        Aliases expectedAliases = AliasesMother.idvIdAndCreditCardNumber();

        assertThat(identity.getAliases()).isEqualTo(expectedAliases.add(request.getProvidedAlias()));
    }

    @Test
    void shouldFindStubbedIdentityWithPhoneNumbers() {
        FindIdentityRequest request = FindIdentityRequestMother.build();

        Identity identity = finder.find(request);

        assertThat(identity.getPhoneNumbers()).isEqualTo(PhoneNumbersMother.mobileAndOther());
    }

    @Test
    void shouldFindStubbedIdentityWithEmailAddresses() {
        FindIdentityRequest request = FindIdentityRequestMother.build();

        Identity identity = finder.find(request);

        assertThat(identity.getEmailAddresses()).containsExactlyElementsOf(EmailAddressesMother.two());
    }

}
