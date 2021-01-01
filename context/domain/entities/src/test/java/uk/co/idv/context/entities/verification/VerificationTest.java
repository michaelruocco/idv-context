package uk.co.idv.context.entities.verification;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.context.method.Methods;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class VerificationTest {

    @Test
    void shouldReturnId() {
        UUID id = UUID.randomUUID();

        Verification verification = Verification.builder()
                .id(id)
                .build();

        assertThat(verification.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnContextId() {
        UUID contextId = UUID.randomUUID();

        Verification verification = Verification.builder()
                .contextId(contextId)
                .build();

        assertThat(verification.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnActivity() {
        Activity activity = mock(Activity.class);

        Verification verification = Verification.builder()
                .activity(activity)
                .build();

        assertThat(verification.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldReturnMethods() {
        Methods methods = mock(Methods.class);

        Verification verification = Verification.builder()
                .methods(methods)
                .build();

        assertThat(verification.getMethods()).isEqualTo(methods);
    }

    @Test
    void shouldReturnProtectSensitiveData() {
        Verification verification = Verification.builder()
                .protectSensitiveData(true)
                .build();

        assertThat(verification.isProtectSensitiveData()).isTrue();
    }

    @Test
    void shouldNotBeCompletedByDefault() {
        Verification verification = Verification.builder()
                .build();

        assertThat(verification.getCompleted()).isEmpty();
        assertThat(verification.isComplete()).isFalse();
    }

    @Test
    void shouldNotBeSuccessfulByDefault() {
        Verification verification = Verification.builder()
                .build();

        assertThat(verification.isSuccessful()).isFalse();
    }

    @Test
    void shouldCompleteVerification() {
        Verification verification = VerificationMother.incomplete();

        CompleteVerificationRequest request = CompleteVerificationRequestMother.successful();

        Verification completed = verification.complete(request);

        assertThat(completed)
                .usingRecursiveComparison()
                .ignoringFields("complete", "completed", "successful")
                .isEqualTo(verification);
        assertThat(completed.isComplete()).isTrue();
        assertThat(completed.getCompleted()).contains(request.forceGetTimestamp());
    }

}
