package uk.co.idv.context.adapter.verification.client;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequest;
import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequestMother;
import uk.co.idv.context.adapter.verification.client.request.RequestConverter;
import uk.co.idv.context.adapter.verification.client.request.UriFactory;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.VerificationMother;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class RestVerificationClientTest {

    private final RequestConverter requestConverter = mock(RequestConverter.class);
    private final ResponseConverter responseConverter = mock(ResponseConverter.class);
    private final RequestExecutor executor = mock(RequestExecutor.class);

    private final VerificationClient client = RestVerificationClient.builder()
            .requestConverter(requestConverter)
            .responseConverter(responseConverter)
            .executor(executor)
            .build();

    @Test
    void shouldCreateVerification() {
        ClientCreateVerificationRequest request = ClientCreateVerificationRequestMother.build();
        HttpRequest httpRequest = givenConvertsToHttpRequest(request);
        HttpResponse<String> httpResponse = givenExecutingRequestReturns(httpRequest);
        Verification expectedVerification = givenConvertsToVerification(httpResponse);

        Verification verification = client.createVerification(request);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    private HttpRequest givenConvertsToHttpRequest(ClientCreateVerificationRequest request) {
        HttpRequest httpRequest = buildHttpRequest();
        given(requestConverter.toPostVerificationHttpRequest(request)).willReturn(httpRequest);
        return httpRequest;
    }

    private HttpResponse<String> givenExecutingRequestReturns(HttpRequest httpRequest) {
        HttpResponse<String> httpResponse = mock(HttpResponse.class);
        given(executor.execute(httpRequest)).willReturn(httpResponse);
        return httpResponse;
    }

    private Verification givenConvertsToVerification(HttpResponse<String> httpResponse) {
        Verification verification = VerificationMother.incomplete();
        given(responseConverter.toVerificationOrThrowError(httpResponse)).willReturn(verification);
        return verification;
    }

    private static HttpRequest buildHttpRequest() {
        return HttpRequest.newBuilder()
                .uri(UriFactory.toUri("http://localhost:8080/text"))
                .build();
    }

}
