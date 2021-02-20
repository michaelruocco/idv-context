package uk.co.idv.context.usecases.context.verification;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.verification.CompleteVerificationRequest;
import uk.co.idv.method.entities.verification.CompleteVerificationRequestMother;
import uk.co.idv.method.entities.verification.CreateVerificationRequest;
import uk.co.idv.method.entities.verification.CreateVerificationRequestMother;
import uk.co.idv.method.entities.verification.GetVerificationRequest;
import uk.co.idv.method.entities.verification.GetVerificationRequestMother;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.VerificationMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationServiceTest {

    private final CreateVerification createVerification = mock(CreateVerification.class);
    private final CompleteVerification completeVerification = mock(CompleteVerification.class);
    private final GetVerification getVerification = mock(GetVerification.class);

    private final VerificationService verificationService = VerificationService.builder()
            .createVerification(createVerification)
            .completeVerification(completeVerification)
            .getVerification(getVerification)
            .build();

    @Test
    void shouldCreateVerification() {
        CreateVerificationRequest request = CreateVerificationRequestMother.build();
        Verification expected = VerificationMother.incomplete();
        given(createVerification.create(request)).willReturn(expected);

        Verification created = verificationService.create(request);

        assertThat(created).isEqualTo(expected);
    }

    @Test
    void shouldGetVerification() {
        GetVerificationRequest request = GetVerificationRequestMother.build();
        Verification expected = VerificationMother.incomplete();
        given(getVerification.get(request)).willReturn(expected);

        Verification verification = verificationService.get(request);

        assertThat(verification).isEqualTo(expected);
    }

    @Test
    void shouldCompleteVerification() {
        CompleteVerificationRequest request = CompleteVerificationRequestMother.successful();
        Verification expected = VerificationMother.incomplete();
        given(completeVerification.complete(request)).willReturn(expected);

        Verification completed = verificationService.complete(request);

        assertThat(completed).isEqualTo(expected);
    }

}
