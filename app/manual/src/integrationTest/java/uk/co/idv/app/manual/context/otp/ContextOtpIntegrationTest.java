package uk.co.idv.app.manual.context.otp;

import org.junit.jupiter.api.Test;
import uk.co.idv.app.manual.Application;
import uk.co.idv.app.manual.TestHarness;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.context.entities.policy.sequence.SequencePoliciesMother;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;
import uk.co.idv.method.entities.otp.GetOtpIfNextEligible;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.delivery.query.DeliveryMethodEligibilityIncomplete;
import uk.co.idv.method.entities.otp.policy.OtpPolicyMother;
import uk.co.idv.policy.entities.policy.key.ChannelPolicyKeyMother;

import java.time.Duration;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class ContextOtpIntegrationTest {

    private final TestHarness harness = new TestHarness();
    private final Application application = harness.getApplication();

    @Test
    void shouldPopulateOtpMethodOnContextIfOtpPolicyConfigured() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        ContextPolicy policy = ContextPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(request.getChannelId()))
                .sequencePolicies(SequencePoliciesMother.withMethodPolicy(OtpPolicyMother.build()))
                .build();
        application.create(policy);
        harness.givenContextPolicyExistsForChannel(request.getChannelId(), OtpPolicyMother.build());
        harness.givenIdentityExistsForAliases(request.getAliases());
        harness.givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = application.create(request);

        Stream<Otp> otps = context.query(new GetOtpIfNextEligible());
        assertThat(otps).hasSize(1);
    }

    @Test
    void shouldReturnIncompleteDeliveryMethodOnCreateAndCompleteOnGetIfAsyncSimSwapOtpPolicyConfigured() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        ContextPolicy policy = ContextPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(request.getChannelId()))
                .sequencePolicies(SequencePoliciesMother.withMethodPolicy(OtpPolicyMother.withSmsAsyncSimSwap()))
                .build();
        application.create(policy);
        harness.givenContextPolicyExistsForChannel(request.getChannelId(), OtpPolicyMother.build());
        PhoneNumber phoneNumber = PhoneNumberMother.delayedSimSwapMobileNumber();
        Identity identity = IdentityMother.exampleBuilder()
                .aliases(request.getAliases())
                .phoneNumbers(PhoneNumbersMother.with(phoneNumber))
                .emailAddresses(EmailAddressesMother.empty())
                .build();
        harness.givenIdentityExists(identity);
        harness.givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = application.create(request);

        UUID deliveryMethodId = UUID.fromString("446846e6-bf16-4da5-af5b-9ad4a240fe5d");
        assertThat(context.query(new DeliveryMethodEligibilityIncomplete(deliveryMethodId))).isTrue();

        DeliveryMethodEligibleAndEligibilityComplete deliveryMethodEligible = DeliveryMethodEligibleAndEligibilityComplete.builder()
                .application(application)
                .contextId(context.getId())
                .deliveryMethodId(deliveryMethodId)
                .build();

        await().pollDelay(Duration.ofSeconds(3))
                .pollInterval(Duration.ofMillis(250))
                .atMost(Duration.ofSeconds(4))
                .until(deliveryMethodEligible);

        assertThat(deliveryMethodEligible.isSuccessful()).isTrue();
    }

}
