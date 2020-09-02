package uk.co.idv.identity.entities.identity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestedDataTest {

    @Test
    void shouldReturnEmailAddressesRequestedTrueIfRequested() {
        RequestedData requestedData = RequestedDataMother.onlyEmailAddressesRequested();

        boolean isRequested = requestedData.emailAddressesRequested();

        assertThat(isRequested).isTrue();
    }

    @Test
    void shouldReturnEmailAddressesRequestedFalseIfNotRequested() {
        RequestedData requestedData = RequestedDataMother.noneRequested();

        boolean isRequested = requestedData.emailAddressesRequested();

        assertThat(isRequested).isFalse();
    }

    @Test
    void shouldReturnPhoneNumbersRequestedTrueIfRequested() {
        RequestedData requestedData = RequestedDataMother.onlyPhoneNumbersRequested();

        boolean isRequested = requestedData.phoneNumbersRequested();

        assertThat(isRequested).isTrue();
    }

    @Test
    void shouldReturnPhoneNumbersRequestedFalseIfNotRequested() {
        RequestedData requestedData = RequestedDataMother.noneRequested();

        boolean isRequested = requestedData.phoneNumbersRequested();

        assertThat(isRequested).isFalse();
    }

    @Test
    void shouldBeIterable() {
        RequestedData requestedData = RequestedDataMother.allRequested();

        assertThat(requestedData).containsExactly(
                RequestedData.EMAIL_ADDRESSES,
                RequestedData.PHONE_NUMBERS
        );
    }

}
