package uk.co.idv.context.adapter.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import uk.co.idv.common.adapter.json.error.internalserver.InternalServerErrorJsonMother;
import uk.co.idv.common.adapter.json.error.internalserver.InternalServerErrorMother;
import uk.co.idv.context.adapter.client.exception.ApiErrorClientException;
import uk.co.idv.context.adapter.client.header.ContextRequestHeaders;
import uk.co.idv.context.adapter.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.context.adapter.client.request.ClientCreateVerificationRequest;
import uk.co.idv.context.adapter.json.context.error.contextexpired.ContextExpiredErrorJsonMother;
import uk.co.idv.context.adapter.json.context.error.contextexpired.ContextExpiredErrorMother;
import uk.co.idv.context.adapter.json.context.error.contextnotfound.ContextNotFoundErrorJsonMother;
import uk.co.idv.context.adapter.json.context.error.contextnotfound.ContextNotFoundErrorMother;
import uk.co.idv.context.adapter.json.context.error.notnextmethod.NotNextMethodErrorJsonMother;
import uk.co.idv.context.adapter.json.context.error.notnextmethod.NotNextMethodErrorMother;
import uk.co.idv.method.adapter.json.verification.VerificationJsonMother;
import uk.co.idv.method.entities.verification.CompleteVerificationRequest;
import uk.co.idv.method.entities.verification.CompleteVerificationRequestMother;
import uk.co.idv.method.entities.verification.CreateVerificationRequest;
import uk.co.idv.method.entities.verification.CreateVerificationRequestMother;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.VerificationMother;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.patch;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static uk.co.idv.context.adapter.client.header.HeaderConstants.ACCEPT_NAME;
import static uk.co.idv.context.adapter.client.header.HeaderConstants.APPLICATION_JSON;
import static uk.co.idv.context.adapter.client.header.HeaderConstants.CHANNEL_ID_NAME;
import static uk.co.idv.context.adapter.client.header.HeaderConstants.CONTENT_TYPE_NAME;
import static uk.co.idv.context.adapter.client.header.HeaderConstants.CORRELATION_ID_NAME;

class RestVerificationClientIntegrationTest {

    @RegisterExtension
    public static final WireMockExtension SERVER = new WireMockExtension();

    private static final String VERIFICATION_URL = "/v1/contexts/verifications";

    private static final ObjectMapper MAPPER = JsonConverterFactory.buildMapper();

    private VerificationClient client;

    @BeforeEach
    public void setUp() {
        RestVerificationClientConfig config = RestVerificationClientConfig.builder()
                .baseUri(SERVER.getBaseUrl())
                .mapper(MAPPER)
                .build();
        client = RestVerificationClient.build(config);
    }

    @Test
    void shouldThrowExceptionIfContextNotFoundErrorReturnedFromCreateVerification() {
        CreateVerificationRequest request = CreateVerificationRequestMother.build();
        ClientCreateVerificationRequest clientRequest = toClientRequest(request);
        SERVER.stubFor(configureUpdateHeaders(post(urlEqualTo(VERIFICATION_URL)), clientRequest.getHeaders())
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody(ContextNotFoundErrorJsonMother.contextNotFoundErrorJson())));

        ApiErrorClientException error = catchThrowableOfType(
                () -> client.createVerification(clientRequest),
                ApiErrorClientException.class
        );

        assertThat(error.getError())
                .usingRecursiveComparison()
                .isEqualTo(ContextNotFoundErrorMother.contextNotFoundError());
    }

    @Test
    void shouldThrowExceptionIfContextExpiredErrorReturnedFromCreateVerification() {
        CreateVerificationRequest request = CreateVerificationRequestMother.build();
        ClientCreateVerificationRequest clientRequest = toClientRequest(request);
        SERVER.stubFor(configureUpdateHeaders(post(urlEqualTo(VERIFICATION_URL)), clientRequest.getHeaders())
                .willReturn(aResponse()
                        .withStatus(410)
                        .withBody(ContextExpiredErrorJsonMother.contextExpiredErrorJson())));

        ApiErrorClientException error = catchThrowableOfType(
                () -> client.createVerification(clientRequest),
                ApiErrorClientException.class
        );

        assertThat(error.getError())
                .usingRecursiveComparison()
                .isEqualTo(ContextExpiredErrorMother.contextExpiredError());
    }

    @Test
    void shouldThrowExceptionIfNotNextMethodReturnedFromCreateVerification() {
        CreateVerificationRequest request = CreateVerificationRequestMother.build();
        ClientCreateVerificationRequest clientRequest = toClientRequest(request);
        SERVER.stubFor(configureUpdateHeaders(post(urlEqualTo(VERIFICATION_URL)), clientRequest.getHeaders())
                .willReturn(aResponse()
                        .withStatus(422)
                        .withBody(NotNextMethodErrorJsonMother.notNextMethodErrorJson())));

        ApiErrorClientException error = catchThrowableOfType(
                () -> client.createVerification(clientRequest),
                ApiErrorClientException.class
        );

        assertThat(error.getError())
                .usingRecursiveComparison()
                .isEqualTo(NotNextMethodErrorMother.notNextMethodError());
    }

    @Test
    void shouldReturnVerificationFromSuccessfulCreateVerification() {
        CreateVerificationRequest request = CreateVerificationRequestMother.build();
        ClientCreateVerificationRequest clientRequest = toClientRequest(request);
        SERVER.stubFor(configureUpdateHeaders(post(urlEqualTo(VERIFICATION_URL)), clientRequest.getHeaders())
                .willReturn(aResponse()
                        .withStatus(201)
                        .withBody(VerificationJsonMother.incomplete())));

        Verification verification = client.createVerification(clientRequest);

        assertThat(verification).isEqualTo(VerificationMother.incomplete());
    }

    @Test
    void shouldThrowExceptionIfContextNotFoundErrorReturnedFromCompleteVerification() {
        CompleteVerificationRequest request = CompleteVerificationRequestMother.successful();
        ClientCompleteVerificationRequest clientRequest = toClientRequest(request);
        SERVER.stubFor(configureUpdateHeaders(patch(urlEqualTo(VERIFICATION_URL)), clientRequest.getHeaders())
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody(ContextNotFoundErrorJsonMother.contextNotFoundErrorJson())));

        ApiErrorClientException error = catchThrowableOfType(
                () -> client.completeVerification(clientRequest),
                ApiErrorClientException.class
        );

        assertThat(error.getError())
                .usingRecursiveComparison()
                .isEqualTo(ContextNotFoundErrorMother.contextNotFoundError());
    }

    @Test
    void shouldThrowExceptionIfContextExpiredErrorReturnedFromCompleteVerification() {
        CompleteVerificationRequest request = CompleteVerificationRequestMother.successful();
        ClientCompleteVerificationRequest clientRequest = toClientRequest(request);
        SERVER.stubFor(configureUpdateHeaders(patch(urlEqualTo(VERIFICATION_URL)), clientRequest.getHeaders())
                .willReturn(aResponse()
                        .withStatus(410)
                        .withBody(ContextExpiredErrorJsonMother.contextExpiredErrorJson())));

        ApiErrorClientException error = catchThrowableOfType(
                () -> client.completeVerification(clientRequest),
                ApiErrorClientException.class
        );

        assertThat(error.getError())
                .usingRecursiveComparison()
                .isEqualTo(ContextExpiredErrorMother.contextExpiredError());
    }

    @Test
    void shouldThrowExceptionIfVerificationNotFoundReturnedFromCompleteVerification() {
        CompleteVerificationRequest request = CompleteVerificationRequestMother.successful();
        ClientCompleteVerificationRequest clientRequest = toClientRequest(request);
        SERVER.stubFor(configureUpdateHeaders(patch(urlEqualTo(VERIFICATION_URL)), clientRequest.getHeaders())
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody(InternalServerErrorJsonMother.internalServerErrorJson())));

        ApiErrorClientException error = catchThrowableOfType(
                () -> client.completeVerification(clientRequest),
                ApiErrorClientException.class
        );

        assertThat(error.getError())
                .usingRecursiveComparison()
                .isEqualTo(InternalServerErrorMother.internalServerError());
    }

    @Test
    void shouldReturnVerificationFromSuccessfulCompleteVerification() {
        CompleteVerificationRequest request = CompleteVerificationRequestMother.successful();
        ClientCompleteVerificationRequest clientRequest = toClientRequest(request);
        SERVER.stubFor(configureUpdateHeaders(patch(urlEqualTo(VERIFICATION_URL)), clientRequest.getHeaders())
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(VerificationJsonMother.successful())));

        Verification verification = client.completeVerification(clientRequest);

        assertThat(verification).isEqualTo(VerificationMother.successful());
    }

    private ClientCreateVerificationRequest toClientRequest(CreateVerificationRequest request) {
        return ClientCreateVerificationRequest.builder()
                .body(request)
                .headers(buildHeaders())
                .build();
    }

    private ClientCompleteVerificationRequest toClientRequest(CompleteVerificationRequest request) {
        return ClientCompleteVerificationRequest.builder()
                .body(request)
                .headers(buildHeaders())
                .build();
    }

    private ContextRequestHeaders buildHeaders() {
        return ContextRequestHeaders.build("abc");
    }

    private static MappingBuilder configureUpdateHeaders(MappingBuilder builder, ContextRequestHeaders headers) {
        return configureGetHeaders(builder, headers)
                .withHeader(CONTENT_TYPE_NAME, equalTo(APPLICATION_JSON));
    }

    private static MappingBuilder configureGetHeaders(MappingBuilder builder, ContextRequestHeaders headers) {
        return builder
                .withHeader(ACCEPT_NAME, equalTo(APPLICATION_JSON))
                .withHeader(CORRELATION_ID_NAME, equalTo(headers.getCorrelationId().toString()))
                .withHeader(CHANNEL_ID_NAME, equalTo(headers.getChannelId()));
    }

}
