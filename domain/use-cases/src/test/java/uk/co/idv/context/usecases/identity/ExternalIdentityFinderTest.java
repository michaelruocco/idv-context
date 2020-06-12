package uk.co.idv.context.usecases.identity;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;
import uk.co.idv.context.usecases.identity.data.AliasSupplier;
import uk.co.idv.context.usecases.identity.data.AsyncDataLoader;
import uk.co.idv.context.usecases.identity.data.DataFutures;
import uk.co.idv.context.usecases.identity.data.DataSupplierFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ExternalIdentityFinderTest {

    private final Aliases aliases = mock(Aliases.class);
    private final Aliases updatedAliases = mock(Aliases.class);
    private final FindIdentityRequest request = mock(FindIdentityRequest.class);
    private final FindIdentityRequest updatedRequest = mock(FindIdentityRequest.class);
    private final DataSupplierFactory factory = mock(DataSupplierFactory.class);
    private final AliasSupplier aliasSupplier = mock(AliasSupplier.class);
    private final AsyncDataLoader dataLoader = mock(AsyncDataLoader.class);
    private final DataFutures dataFutures = mock(DataFutures.class);

    private final ExternalIdentityFinder identityFinder = ExternalIdentityFinder.builder()
            .factory(factory)
            .dataLoader(dataLoader)
            .build();

    @BeforeEach
    void setUp() {
        given(factory.aliasesSupplier(request)).willReturn(aliasSupplier);
        given(aliasSupplier.get()).willReturn(aliases);
        given(aliases.add(request.getProvidedAlias())).willReturn(updatedAliases);
        given(request.setAliases(updatedAliases)).willReturn(updatedRequest);
        given(updatedRequest.getAliases()).willReturn(updatedAliases);
        given(dataLoader.loadData(updatedRequest)).willReturn(dataFutures);
    }

    @Test
    void shouldReturnIdentityWithAliasesFromUpdatedRequest() {
        Identity identity = identityFinder.find(request);

        assertThat(identity.getAliases()).isEqualTo(updatedAliases);
    }

    @Test
    void shouldReturnIdentityWithCountryFromUpdatedRequest() {
        CountryCode country = CountryCode.GB;
        given(updatedRequest.getCountry()).willReturn(country);

        Identity identity = identityFinder.find(request);

        assertThat(identity.getCountry()).isEqualTo(country);
    }

    @Test
    void shouldReturnPhoneNumbersFromDataFutures() {
        PhoneNumbers phoneNumbers = PhoneNumbersMother.mobileAndOther();
        given(dataFutures.getPhoneNumbersNow()).willReturn(phoneNumbers);

        Identity identity = identityFinder.find(request);

        assertThat(identity.getPhoneNumbers()).isEqualTo(phoneNumbers);
    }

    @Test
    void shouldReturnEmailAddressesFromDataFutures() {
        EmailAddresses emailAddresses = EmailAddressesMother.two();
        given(dataFutures.getEmailAddressesNow()).willReturn(emailAddresses);

        Identity identity = identityFinder.find(request);

        assertThat(identity.getEmailAddresses()).isEqualTo(emailAddresses);
    }

}
