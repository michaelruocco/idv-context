package uk.co.idv.identity.usecases.eligibility.external;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;
import uk.co.idv.identity.entities.mobiledevice.MobileDevicesMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;
import uk.co.idv.identity.usecases.eligibility.external.data.AliasLoader;
import uk.co.idv.identity.usecases.eligibility.external.data.AsyncDataLoadRequest;
import uk.co.idv.identity.usecases.eligibility.external.data.AsyncDataLoader;
import uk.co.idv.identity.usecases.eligibility.external.data.DataFutures;
import uk.co.idv.identity.usecases.eligibility.external.data.FindIdentityRequestConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ExternalFindIdentityTest {

    private final Aliases loadedAliases = mock(Aliases.class);
    private final Aliases updatedAliases = mock(Aliases.class);
    private final FindIdentityRequest findRequest = mock(FindIdentityRequest.class);
    private final AsyncDataLoadRequest loadRequest = mock(AsyncDataLoadRequest.class);
    private final DataFutures dataFutures = mock(DataFutures.class);

    private final FindIdentityRequestConverter converter = mock(FindIdentityRequestConverter.class);
    private final AliasLoader aliasLoader = mock(AliasLoader.class);
    private final AsyncDataLoader dataLoader = mock(AsyncDataLoader.class);

    private final ExternalFindIdentity identityFinder = ExternalFindIdentity.builder()
            .converter(converter)
            .aliasLoader(aliasLoader)
            .dataLoader(dataLoader)
            .build();

    @BeforeEach
    void setUp() {
        given(aliasLoader.load(findRequest)).willReturn(loadedAliases);
        given(loadedAliases.add(findRequest.getAliases())).willReturn(updatedAliases);
        given(converter.toAsyncDataLoadRequest(updatedAliases, findRequest)).willReturn(loadRequest);
        given(dataLoader.loadData(loadRequest)).willReturn(dataFutures);
    }

    @Test
    void shouldReturnIdentityWithAliasesFromUpdatedRequest() {
        Identity identity = identityFinder.find(findRequest);

        assertThat(identity.getAliases()).isEqualTo(updatedAliases);
    }

    @Test
    void shouldReturnIdentityWithCountryFromFindRequest() {
        CountryCode country = CountryCode.GB;
        given(findRequest.getCountry()).willReturn(country);

        Identity identity = identityFinder.find(findRequest);

        assertThat(identity.getCountry()).isEqualTo(country);
    }

    @Test
    void shouldReturnPhoneNumbersFromDataFutures() {
        PhoneNumbers phoneNumbers = PhoneNumbersMother.two();
        given(dataFutures.getPhoneNumbersNow()).willReturn(phoneNumbers);

        Identity identity = identityFinder.find(findRequest);

        assertThat(identity.getPhoneNumbers()).isEqualTo(phoneNumbers);
    }

    @Test
    void shouldReturnEmailAddressesFromDataFutures() {
        EmailAddresses emailAddresses = EmailAddressesMother.two();
        given(dataFutures.getEmailAddressesNow()).willReturn(emailAddresses);

        Identity identity = identityFinder.find(findRequest);

        assertThat(identity.getEmailAddresses()).isEqualTo(emailAddresses);
    }

    @Test
    void shouldReturnMobileDevicesFromDataFutures() {
        MobileDevices mobileDevices = MobileDevicesMother.two();
        given(dataFutures.getMobileDevicesNow()).willReturn(mobileDevices);

        Identity identity = identityFinder.find(findRequest);

        assertThat(identity.getMobileDevices()).isEqualTo(mobileDevices);
    }

}
