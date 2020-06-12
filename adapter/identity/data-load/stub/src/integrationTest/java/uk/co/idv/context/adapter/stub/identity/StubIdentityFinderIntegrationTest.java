package uk.co.idv.context.adapter.stub.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.IdentityFinder;
import uk.co.idv.context.usecases.identity.FindIdentityRequestMother;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

public class StubIdentityFinderIntegrationTest {

    private final ExecutorService executor = Executors.newFixedThreadPool(1);
    private final StubIdentityFinderConfig config = StubIdentityFinderConfig.build(executor);
    private final IdentityFinder finder = new StubIdentityFinder(config);

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
