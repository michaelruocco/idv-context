package uk.co.idv.app.spring.context;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.idv.app.manual.Application;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.context.entities.verification.CreateVerificationRequest;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.context.entities.result.FacadeRecordResultRequest;
import uk.co.idv.context.entities.result.FacadeRecordResultRequestMother;

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

    private final ContextController controller = new ContextController(application);

    @Test
    void shouldCreateContext() {
        FacadeCreateContextRequest request = FacadeCreateContextRequestMother.build();
        Context expectedContext = givenContextCreatedFor(request);

        ResponseEntity<Context> response = controller.createContext(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(expectedContext);
    }

    @Test
    void shouldReturnLocationForCreatedContext() {
        FacadeCreateContextRequest request = FacadeCreateContextRequestMother.build();
        Context expectedContext = givenContextCreatedFor(request);

        ResponseEntity<Context> response = controller.createContext(request);

        String expectedLocation = String.format("/v1/contexts/%s", expectedContext.getId());
        assertThat(response.getHeaders()).contains(
                entry("Location", singletonList(expectedLocation))
        );
    }

    @Test
    void shouldGetContext() {
        UUID id = UUID.randomUUID();
        Context expectedContext = givenContextLoadedFor(id);

        Context context = controller.getContext(id);

        assertThat(context).isEqualTo(expectedContext);
    }

    @Test
    void shouldRecordResult() {
        FacadeRecordResultRequest request = FacadeRecordResultRequestMother.build();
        Context expectedContext = givenContextUpdatedFor(request);

        Context context = controller.recordResult(request);

        assertThat(context).isEqualTo(expectedContext);
    }

    @Test
    void shouldPassIdToNextMethodsRequest() {
        UUID id = UUID.randomUUID();

        controller.getNextMethods(id, "fake-method");

        ArgumentCaptor<CreateVerificationRequest> captor = ArgumentCaptor.forClass(CreateVerificationRequest.class);
        verify(application).findNextMethods(captor.capture());
        CreateVerificationRequest request = captor.getValue();
        assertThat(request.getContextId()).isEqualTo(id);
    }

    @Test
    void shouldPassMethodNameToNextMethodsRequest() {
        String methodName = "fake-method";

        controller.getNextMethods(UUID.randomUUID(), methodName);

        ArgumentCaptor<CreateVerificationRequest> captor = ArgumentCaptor.forClass(CreateVerificationRequest.class);
        verify(application).findNextMethods(captor.capture());
        CreateVerificationRequest request = captor.getValue();
        assertThat(request.getMethodName()).isEqualTo(methodName);
    }

    @Test
    void shouldReturnNextMethods() {
        Verification expectedVerification = mock(Verification.class);
        given(application.findNextMethods(any(CreateVerificationRequest.class))).willReturn(expectedVerification);

        Verification verification = controller.getNextMethods(UUID.randomUUID(), "fake-method");

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

    private Context givenContextUpdatedFor(FacadeRecordResultRequest request) {
        Context context = ContextMother.build();
        given(application.record(request)).willReturn(context);
        return context;
    }

}
