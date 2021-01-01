package uk.co.idv.context.usecases.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.verification.CompleteVerificationRequest;
import uk.co.idv.context.entities.verification.CompleteVerificationRequestMother;
import uk.co.idv.context.entities.verification.GetVerificationRequest;
import uk.co.idv.context.entities.verification.GetVerificationRequestMother;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.context.entities.verification.CreateVerificationRequest;
import uk.co.idv.context.entities.verification.CreateVerificationRequestMother;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.usecases.context.identity.IdentityLoader;
import uk.co.idv.context.usecases.context.verification.VerificationService;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ContextFacadeTest {

    private final IdentityLoader identityLoader = mock(IdentityLoader.class);
    private final ContextService contextService = mock(ContextService.class);
    private final VerificationService verificationService = mock(VerificationService.class);

    private final ContextFacade facade = ContextFacade.builder()
            .identityLoader(identityLoader)
            .contextService(contextService)
            .verificationService(verificationService)
            .build();

    @Test
    void shouldCreateContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        ServiceCreateContextRequest identityRequest = givenIdentityRequestLoaded(request);
        Context expectedContext = givenContextCreated(identityRequest);

        Context context = facade.create(request);

        assertThat(context).isEqualTo(expectedContext);
    }

    @Test
    void shouldFindContext() {
        UUID id = UUID.randomUUID();
        Context expectedContext = givenContextFound(id);

        Context context = facade.find(id);

        assertThat(context).isEqualTo(expectedContext);
    }

    @Test
    void shouldCreateVerification() {
        CreateVerificationRequest request = CreateVerificationRequestMother.build();
        Verification expected = givenCreatedVerification(request);

        Verification created = facade.create(request);

        assertThat(created).isEqualTo(expected);
    }

    @Test
    void shouldGetVerification() {
        GetVerificationRequest request = GetVerificationRequestMother.build();
        Verification expected = givenVerificationFound(request);

        Verification verification = facade.get(request);

        assertThat(verification).isEqualTo(expected);
    }

    @Test
    void shouldCompleteVerification() {
        CompleteVerificationRequest request = CompleteVerificationRequestMother.successful();
        Verification expected = givenCompletedVerification(request);

        Verification completed = facade.complete(request);

        assertThat(completed).isEqualTo(expected);
    }

    private ServiceCreateContextRequest givenIdentityRequestLoaded(CreateContextRequest initialRequest) {
        ServiceCreateContextRequest identityRequest = mock(ServiceCreateContextRequest.class);
        given(identityLoader.addIdentity(initialRequest)).willReturn(identityRequest);
        return identityRequest;
    }

    private Context givenContextCreated(ServiceCreateContextRequest request) {
        Context context = mock(Context.class);
        given(contextService.create(request)).willReturn(context);
        return context;
    }

    private Context givenContextFound(UUID id) {
        Context context = mock(Context.class);
        given(contextService.find(id)).willReturn(context);
        return context;
    }

    private Verification givenCreatedVerification(CreateVerificationRequest request) {
        Verification verification = mock(Verification.class);
        given(verificationService.create(request)).willReturn(verification);
        return verification;
    }

    private Verification givenVerificationFound(GetVerificationRequest request) {
        Verification verification = mock(Verification.class);
        given(verificationService.get(request)).willReturn(verification);
        return verification;
    }

    private Verification givenCompletedVerification(CompleteVerificationRequest request) {
        Verification verification = mock(Verification.class);
        given(verificationService.complete(request)).willReturn(verification);
        return verification;
    }

}
