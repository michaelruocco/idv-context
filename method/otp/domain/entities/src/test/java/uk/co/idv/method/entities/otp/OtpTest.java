package uk.co.idv.method.entities.otp;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.method.MethodVerifications;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodsMother;
import uk.co.idv.method.entities.otp.delivery.query.DeliveryMethodNotFoundException;

import java.util.Optional;
import java.util.UUID;
import java.util.function.UnaryOperator;

import static java.lang.Integer.toUnsignedLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OtpTest {

    @Test
    void shouldReturnName() {
        Otp otp = Otp.builder().build();

        assertThat(otp.getName()).isEqualTo("one-time-passcode");
    }

    @Test
    void shouldReturnDeliveryMethods() {
        DeliveryMethods deliveryMethods = givenDeliveryMethods();

        Otp otp = Otp.builder()
                .deliveryMethods(deliveryMethods)
                .build();

        assertThat(otp.getDeliveryMethods()).isEqualTo(deliveryMethods);
    }

    @Test
    void shouldReturnEligibilityFromDeliveryMethods() {
        Eligibility eligibility = mock(Eligibility.class);
        DeliveryMethods deliveryMethods = givenDeliveryMethodsWithEligibility(eligibility);

        Otp otp = Otp.builder()
                .deliveryMethods(deliveryMethods)
                .build();

        assertThat(otp.getEligibility()).isEqualTo(eligibility);
    }

    @Test
    void shouldReturnSuccessful() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Otp otp = Otp.builder().build();
        given(verifications.containsSuccessful(otp.getName())).willReturn(true);

        boolean successful = otp.isSuccessful(verifications);

        assertThat(successful).isTrue();
    }

    @Test
    void shouldBeCompleteIfSuccessfulVerificationForMethod() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Otp otp = Otp.builder()
                .config(OtpConfigMother.build())
                .build();
        given(verifications.containsSuccessful(otp.getName())).willReturn(true);

        boolean complete = otp.isComplete(verifications);

        assertThat(complete).isTrue();
    }

    @Test
    void shouldBeCompleteIfNoSuccessfulVerificationsForMethodAndNoAttemptsRemaining() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        OtpConfig config = OtpConfigMother.build();
        Otp otp = Otp.builder()
                .config(config)
                .build();
        given(verifications.containsSuccessful(otp.getName())).willReturn(false);
        given(verifications.countByName(otp.getName())).willReturn(toUnsignedLong(config.getMaxNumberOfAttempts()));

        boolean complete = otp.isComplete(verifications);

        assertThat(complete).isTrue();
    }

    @Test
    void shouldNotBeCompleteIfNoSuccessfulVerificationsForMethodAndAttemptsRemaining() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        OtpConfig config = OtpConfigMother.build();
        Otp otp = Otp.builder()
                .config(config)
                .build();
        given(verifications.containsSuccessful(otp.getName())).willReturn(false);
        given(verifications.countByName(otp.getName())).willReturn(toUnsignedLong(config.getMaxNumberOfAttempts() - 1));

        boolean complete = otp.isComplete(verifications);

        assertThat(complete).isFalse();
    }

    @Test
    void shouldReturnMethodConfig() {
        OtpConfig otpConfig = mock(OtpConfig.class);

        Otp otp = Otp.builder()
                .config(otpConfig)
                .build();

        assertThat(otp.getConfig()).isEqualTo(otpConfig);
    }

    @Test
    void shouldReplaceDeliveryMethods() {
        DeliveryMethods deliveryMethods = givenDeliveryMethods();
        DeliveryMethods newValues = givenDeliveryMethods();
        DeliveryMethods replaced = givenDeliveryMethods();
        given(deliveryMethods.replace(newValues)).willReturn(replaced);
        Otp otp = OtpMother.withDeliveryMethods(deliveryMethods);

        Otp updated = otp.replaceDeliveryMethods(newValues);

        assertThat(updated).usingRecursiveComparison()
                .ignoringFields("deliveryMethods")
                .isEqualTo(otp);
        assertThat(updated.getDeliveryMethods()).isEqualTo(replaced);
    }

    @Test
    void shouldUpdateDeliveryMethods() {
        DeliveryMethods deliveryMethods = givenDeliveryMethods();
        DeliveryMethods newValues = givenDeliveryMethods();
        UnaryOperator<DeliveryMethod> update = mock(UnaryOperator.class);
        given(deliveryMethods.update(update)).willReturn(newValues);
        DeliveryMethods replaced = givenDeliveryMethods();
        given(deliveryMethods.replace(newValues)).willReturn(replaced);
        Otp otp = OtpMother.withDeliveryMethods(deliveryMethods);

        Otp updated = otp.updateDeliveryMethods(update);

        assertThat(updated).usingRecursiveComparison()
                .ignoringFields("deliveryMethods")
                .isEqualTo(otp);
        assertThat(updated.getDeliveryMethods()).isEqualTo(replaced);
    }

    @Test
    void shouldReturnTrueIfMethodWithIdIsPresent() {
        DeliveryMethod expectedMethod = DeliveryMethodMother.build();
        Otp otp = OtpMother.withDeliveryMethods(DeliveryMethodsMother.with(expectedMethod));

        boolean contains = otp.containsDeliveryMethod(expectedMethod.getId());

        assertThat(contains).isTrue();
    }

    @Test
    void shouldReturnFalseIfMethodWithIdIsNotPresent() {
        UUID id = UUID.randomUUID();
        Otp otp = OtpMother.withDeliveryMethods(DeliveryMethodsMother.empty());

        boolean contains = otp.containsDeliveryMethod(id);

        assertThat(contains).isFalse();
    }

    @Test
    void shouldReturnTrueIfMethodWithIdIsPresentAndEligible() {
        DeliveryMethod expectedMethod = DeliveryMethodMother.eligible();
        Otp otp = OtpMother.withDeliveryMethods(DeliveryMethodsMother.with(expectedMethod));

        boolean containsEligible = otp.containsEligibleDeliveryMethod(expectedMethod.getId());

        assertThat(containsEligible).isTrue();
    }

    @Test
    void shouldReturnFalseIfMethodWithIdIsPresentAndButNotEligible() {
        DeliveryMethod expectedMethod = DeliveryMethodMother.ineligible();
        Otp otp = OtpMother.withDeliveryMethods(DeliveryMethodsMother.with(expectedMethod));

        boolean containsEligible = otp.containsEligibleDeliveryMethod(expectedMethod.getId());

        assertThat(containsEligible).isFalse();
    }

    @Test
    void shouldReturnContainsEligibleFalseIfMethodWithIdIsNotPresent() {
        Otp otp = OtpMother.withDeliveryMethods(DeliveryMethodsMother.empty());
        UUID id = UUID.fromString("4040c594-0dad-4bbc-b8d3-24a064b5d34a");

        boolean containsEligible = otp.containsEligibleDeliveryMethod(id);

        assertThat(containsEligible).isFalse();
    }

    @Test
    void shouldGetDeliveryMethodFromDeliveryMethodsIfMethodWithIdIsPresent() {
        DeliveryMethod expectedMethod = DeliveryMethodMother.build();
        Otp otp = OtpMother.withDeliveryMethods(DeliveryMethodsMother.with(expectedMethod));

        DeliveryMethod found = otp.getDeliveryMethod(expectedMethod.getId());

        assertThat(found).isEqualTo(expectedMethod);
    }

    @Test
    void shouldThrowExceptionFromGetDeliveryMethodFromDeliveryMethodsIfMethodWithIdIsNotPresent() {
        UUID id = UUID.randomUUID();
        Otp otp = OtpMother.withDeliveryMethods(DeliveryMethodsMother.empty());

        Throwable error = catchThrowable(() -> otp.getDeliveryMethod(id));

        assertThat(error)
                .isInstanceOf(DeliveryMethodNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldFindDeliveryMethodFromDeliveryMethods() {
        DeliveryMethod expectedMethod = DeliveryMethodMother.build();
        Otp otp = OtpMother.withDeliveryMethods(DeliveryMethodsMother.with(expectedMethod));

        Optional<DeliveryMethod> found = otp.findDeliveryMethod(expectedMethod.getId());

        assertThat(found).contains(expectedMethod);
    }

    private DeliveryMethods givenDeliveryMethodsWithEligibility(Eligibility eligibility) {
        DeliveryMethods deliveryMethods = givenDeliveryMethods();
        given(deliveryMethods.getEligibility()).willReturn(eligibility);
        return deliveryMethods;
    }

    private DeliveryMethods givenDeliveryMethods() {
        return mock(DeliveryMethods.class);
    }

}
