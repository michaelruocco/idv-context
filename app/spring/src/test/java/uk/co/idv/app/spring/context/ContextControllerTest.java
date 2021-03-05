package uk.co.idv.app.spring.context;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.idv.app.plain.Application;
import uk.co.idv.context.entities.context.ApiContext;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.context.usecases.context.ContextConverter;
import uk.co.idv.method.entities.verification.CompleteVerificationRequest;
import uk.co.idv.method.entities.verification.CompleteVerificationRequestMother;
import uk.co.idv.method.entities.verification.CreateVerificationRequestMother;
import uk.co.idv.method.entities.verification.GetVerificationRequest;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.CreateVerificationRequest;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.method.entities.verification.VerificationMother;

import java.util.UUID;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ContextControllerTest {

    private final Application application = mock(Application.class);
    private final ContextConverter contextConverter = mock(ContextConverter.class);

    private final ContextController controller = new ContextController(application, contextConverter);

    @Test
    void shouldCreateContext() {
        FacadeCreateContextRequest request = FacadeCreateContextRequestMother.build();
        Context context = givenContextCreatedFor(request);
        ApiContext expectedApiContext = givenConvertedToApiContext(context);

        ResponseEntity<ApiContext> response = controller.createContext(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(expectedApiContext);
    }

    @Test
    void shouldReturnLocationForCreatedContext() {
        FacadeCreateContextRequest request = FacadeCreateContextRequestMother.build();
        Context context = givenContextCreatedFor(request);
        ApiContext expectedApiContext = givenConvertedToApiContext(context);

        ResponseEntity<ApiContext> response = controller.createContext(request);

        String expectedLocation = String.format("/v1/contexts/%s", expectedApiContext.getId());
        assertThat(response.getHeaders()).contains(entry("Location", singletonList(expectedLocation)));
    }

    @Test
    void shouldGetContext() {
        UUID id = UUID.randomUUID();
        Context context = givenContextLoadedFor(id);
        ApiContext expectedApiContext = givenConvertedToApiContext(context);

        ApiContext apiContext = controller.getContext(id);

        assertThat(apiContext).isEqualTo(expectedApiContext);
    }

    @Test
    void shouldCreateVerification() {
        CreateVerificationRequest request = CreateVerificationRequestMother.build();
        Verification expectedVerification = givenVerificationCreatedFor(request);

        ResponseEntity<Verification> response = controller.createVerification(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(expectedVerification);
    }

    @Test
    void shouldReturnLocationForCreatedVerification() {
        CreateVerificationRequest request = CreateVerificationRequestMother.build();
        Verification expectedVerification = givenVerificationCreatedFor(request);

        ResponseEntity<Verification> response = controller.createVerification(request);

        String expectedLocation = String.format("/v1/contexts/%s/verifications/%s",
                request.getContextId(),
                expectedVerification.getId()
        );
        assertThat(response.getHeaders()).contains(entry("Location", singletonList(expectedLocation)));
    }

    @Test
    void shouldPassGetVerificationRequestToApplication() {
        UUID contextId = UUID.randomUUID();
        UUID verificationId = UUID.randomUUID();

        controller.getVerification(contextId, verificationId);

        ArgumentCaptor<GetVerificationRequest> captor = ArgumentCaptor.forClass(GetVerificationRequest.class);
        verify(application).get(captor.capture());
        GetVerificationRequest request = captor.getValue();
        assertThat(request.getContextId()).isEqualTo(contextId);
        assertThat(request.getVerificationId()).isEqualTo(verificationId);
    }

    @Test
    void shouldGetVerificationRequest() {
        UUID contextId = UUID.randomUUID();
        UUID verificationId = UUID.randomUUID();
        Verification expectedVerification = VerificationMother.incomplete();
        given(application.get(any(GetVerificationRequest.class))).willReturn(expectedVerification);

        Verification verification = controller.getVerification(contextId, verificationId);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldCompleteVerification() {
        CompleteVerificationRequest request = CompleteVerificationRequestMother.successful();
        Verification expectedVerification = givenCompletedVerification(request);

        Verification verification = controller.completeVerification(request);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    private Context givenContextCreatedFor(CreateContextRequest request) {
        Context context = ContextMother.build();
        given(application.create(request)).willReturn(context);
        return context;
    }

    private Context givenContextLoadedFor(UUID id) {
        Context context = ContextMother.build();
        given(application.findContext(id)).willReturn(context);
        return context;
    }

    private ApiContext givenConvertedToApiContext(Context context) {
        ApiContext apiContext = mock(ApiContext.class);
        given(apiContext.getId()).willReturn(context.getId());
        given(contextConverter.toApiContext(context)).willReturn(apiContext);
        return apiContext;
    }

    private Verification givenVerificationCreatedFor(CreateVerificationRequest request) {
        Verification verification = VerificationMother.incomplete();
        given(application.create(request)).willReturn(verification);
        return verification;
    }

    private Verification givenCompletedVerification(CompleteVerificationRequest request) {
        Verification verification = VerificationMother.successful();
        given(application.complete(request)).willReturn(verification);
        return verification;
    }

}
