package uk.co.idv.identity.entities.identity;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestedDataTest {

    @Test
    void shouldReturnEmailAddressesRequestedTrueIfRequested() {
        RequestedData requestedData = RequestedDataMother.emailAddressesOnly();

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
        RequestedData requestedData = RequestedDataMother.phoneNumbersOnly();

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
    void shouldReturnMobileDevicesRequestedTrueIfRequested() {
        RequestedData requestedData = RequestedDataMother.mobileDevicesOnly();

        boolean isRequested = requestedData.mobileDevicesRequested();

        assertThat(isRequested).isTrue();
    }

    @Test
    void shouldReturnMobileDevicesRequestedFalseIfNotRequested() {
        RequestedData requestedData = RequestedDataMother.noneRequested();

        boolean isRequested = requestedData.mobileDevicesRequested();

        assertThat(isRequested).isFalse();
    }

    @Test

    void shouldBeIterable() {
        RequestedData requestedData = RequestedDataMother.allRequested();

        assertThat(requestedData).containsExactly(
                RequestedDataItems.EMAIL_ADDRESSES,
                RequestedDataItems.PHONE_NUMBERS,
                RequestedDataItems.MOBILE_DEVICES
        );
    }

    @Test
    void shouldReturnStream() {
        RequestedData requestedData = RequestedDataMother.allRequested();

        Stream<String> items = requestedData.stream();

        assertThat(items).containsExactly(
                RequestedDataItems.EMAIL_ADDRESSES,
                RequestedDataItems.PHONE_NUMBERS,
                RequestedDataItems.MOBILE_DEVICES
        );
    }

    @Test
    void shouldReturnPhoneNumbersOnly() {
        RequestedData requestedData = RequestedDataMother.phoneNumbersOnly();

        assertThat(requestedData).containsExactly(
                RequestedDataItems.PHONE_NUMBERS
        );
    }

    @Test
    void shouldReturnEmailAddressesOnly() {
        RequestedData requestedData = RequestedDataMother.emailAddressesOnly();

        assertThat(requestedData).containsExactly(
                RequestedDataItems.EMAIL_ADDRESSES
        );
    }

    @Test
    void shouldReturnMobileDevicesOnly() {
        RequestedData requestedData = RequestedDataMother.mobileDevicesOnly();

        assertThat(requestedData).containsExactly(
                RequestedDataItems.MOBILE_DEVICES
        );
    }

}
