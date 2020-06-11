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
import uk.co.idv.context.usecases.identity.data.AliasLoader;
import uk.co.idv.context.usecases.identity.data.AsyncDataLoader;
import uk.co.idv.context.usecases.identity.data.DataFutures;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ExternalIdentityLoaderTest {

    private final Aliases aliases = mock(Aliases.class);
    private final Aliases updatedAliases = mock(Aliases.class);
    private final LoadIdentityRequest request = mock(LoadIdentityRequest.class);
    private final LoadIdentityRequest updatedRequest = mock(LoadIdentityRequest.class);
    private final AliasLoader aliasLoader = mock(AliasLoader.class);
    private final AsyncDataLoader dataLoader = mock(AsyncDataLoader.class);
    private final DataFutures dataFutures = mock(DataFutures.class);

    private final ExternalIdentityLoader identityLoader = ExternalIdentityLoader.builder()
            .aliasLoader(aliasLoader)
            .dataLoader(dataLoader)
            .build();

    @BeforeEach
    void setUp() {
        given(aliasLoader.load(request)).willReturn(aliases);
        given(aliases.add(request.getProvidedAlias())).willReturn(updatedAliases);
        given(request.setAliases(updatedAliases)).willReturn(updatedRequest);
        given(updatedRequest.getAliases()).willReturn(updatedAliases);
        given(dataLoader.loadData(updatedRequest)).willReturn(dataFutures);
    }

    @Test
    void shouldReturnIdentityWithAliasesFromUpdatedRequest() {
        Identity identity = identityLoader.load(request);

        assertThat(identity.getAliases()).isEqualTo(updatedAliases);
    }

    @Test
    void shouldReturnIdentityWithCountryFromUpdatedRequest() {
        CountryCode country = CountryCode.GB;
        given(updatedRequest.getCountry()).willReturn(country);

        Identity identity = identityLoader.load(request);

        assertThat(identity.getCountry()).isEqualTo(country);
    }

    @Test
    void shouldReturnPhoneNumbersFromDataFutures() {
        PhoneNumbers phoneNumbers = PhoneNumbersMother.mobileAndOther();
        given(dataFutures.getPhoneNumbersNow()).willReturn(phoneNumbers);

        Identity identity = identityLoader.load(request);

        assertThat(identity.getPhoneNumbers()).isEqualTo(phoneNumbers);
    }

    @Test
    void shouldReturnEmailAddressesFromDataFutures() {
        EmailAddresses emailAddresses = EmailAddressesMother.two();
        given(dataFutures.getEmailAddressesNow()).willReturn(emailAddresses);

        Identity identity = identityLoader.load(request);

        assertThat(identity.getEmailAddresses()).isEqualTo(emailAddresses);
    }

}
