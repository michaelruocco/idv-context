package uk.co.idv.context.usecases.context.verification;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.verification.GetVerificationRequest;
import uk.co.idv.context.entities.verification.GetVerificationRequestMother;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.context.FindContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetVerificationTest {

    private final FindContext findContext = mock(FindContext.class);
    private final ContextRepository repository = mock(ContextRepository.class);

    private final GetVerification getVerification = GetVerification.builder()
            .findContext(findContext)
            .repository(repository)
            .build();

    @Test
    void shouldGetVerification() {
        GetVerificationRequest request = GetVerificationRequestMother.build();
        Context context = givenContextFoundForId(request.getContextId());
        Verification expectedVerification = givenContextHasVerificationForId(context, request.getVerificationId());

        Verification verification = getVerification.get(request);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    private Context givenContextFoundForId(UUID contextId) {
        Context context = mock(Context.class);
        given(findContext.find(contextId)).willReturn(context);
        return context;
    }

    private Verification givenContextHasVerificationForId(Context context, UUID verificationId) {
        Verification verification = mock(Verification.class);
        given(context.getVerification(verificationId)).willReturn(verification);
        return verification;
    }

}
