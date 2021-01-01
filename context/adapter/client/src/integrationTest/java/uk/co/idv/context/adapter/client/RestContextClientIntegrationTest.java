package uk.co.idv.context.adapter.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import uk.co.idv.common.adapter.json.error.internalserver.InternalServerErrorJsonMother;
import uk.co.idv.common.adapter.json.error.internalserver.InternalServerErrorMother;
import uk.co.idv.context.adapter.client.exception.ApiErrorClientException;
import uk.co.idv.context.adapter.client.headers.ContextRequestHeaders;
import uk.co.idv.context.adapter.client.request.ClientCreateContextRequest;
import uk.co.idv.context.adapter.client.request.ClientGetContextRequest;
import uk.co.idv.context.adapter.client.request.ClientCreateVerificationRequest;
import uk.co.idv.context.adapter.json.context.ContextJsonMother;
import uk.co.idv.context.adapter.json.context.error.contextexpired.ContextExpiredErrorJsonMother;
import uk.co.idv.context.adapter.json.context.error.contextexpired.ContextExpiredErrorMother;
import uk.co.idv.context.adapter.json.context.error.contextnotfound.ContextNotFoundErrorJsonMother;
import uk.co.idv.context.adapter.json.context.error.contextnotfound.ContextNotFoundErrorMother;
import uk.co.idv.context.adapter.json.context.error.notnextmethod.NotNextMethodErrorJsonMother;
import uk.co.idv.context.adapter.json.context.error.notnextmethod.NotNextMethodErrorMother;
import uk.co.idv.context.adapter.json.verification.VerificationJsonMother;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.context.entities.verification.CreateVerificationRequest;
import uk.co.idv.context.entities.verification.CreateVerificationRequestMother;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.context.entities.verification.VerificationMother;
import uk.co.idv.identity.adapter.json.error.identitynotfound.IdentityNotFoundErrorJsonMother;
import uk.co.idv.identity.adapter.json.error.identitynotfound.IdentityNotFoundErrorMother;
import uk.co.mruoc.json.JsonConverter;

import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.patch;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static uk.co.idv.context.adapter.client.headers.HeaderConstants.ACCEPT_NAME;
import static uk.co.idv.context.adapter.client.headers.HeaderConstants.APPLICATION_JSON;
import static uk.co.idv.context.adapter.client.headers.HeaderConstants.CHANNEL_ID_NAME;
import static uk.co.idv.context.adapter.client.headers.HeaderConstants.CONTENT_TYPE_NAME;
import static uk.co.idv.context.adapter.client.headers.HeaderConstants.CORRELATION_ID_NAME;

//TODO add tests for get and complete verification
class RestContextClientIntegrationTest {

    @RegisterExtension
    public static final WireMockExtension SERVER = new WireMockExtension();

    private static final String CREATE_CONTEXT_URL = "/v1/contexts";
    private static final String GET_CONTEXT_URL = CREATE_CONTEXT_URL + "/%s";
    private static final String CREATE_VERIFICATION_URL = GET_CONTEXT_URL + "/verifications";

    private static final ObjectMapper MAPPER = JsonConverterFactory.buildMapper();
    private static final JsonConverter JSON_CONVERTER = JsonConverterFactory.build(MAPPER);

    private ContextClient client;

    @BeforeEach
    public void setUp() {
        ContextClientConfig config = RestContextClientConfig.builder()
                .baseUri(SERVER.getBaseUrl())
                .mapper(MAPPER)
                .build();
        client = RestContextClient.build(config);
    }

    @Test
    void shouldThrowExceptionIfIdentityNotFoundErrorReturnedFromCreateContext() {
        FacadeCreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        ContextRequestHeaders headers = ContextRequestHeaders.build(createRequest.getChannelId());
        ClientCreateContextRequest request = ClientCreateContextRequest.builder()
                .body(createRequest)
                .headers(headers)
                .build();

        SERVER.stubFor(configureUpdateHeaders(post(urlEqualTo(CREATE_CONTEXT_URL)), headers)
                .withRequestBody(equalTo(JSON_CONVERTER.toJson(createRequest)))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody(IdentityNotFoundErrorJsonMother.identityNotFoundErrorJson())));

        ApiErrorClientException error = catchThrowableOfType(
                () -> client.createContext(request),
                ApiErrorClientException.class
        );

        assertThat(error.getError())
                .usingRecursiveComparison()
                .isEqualTo(IdentityNotFoundErrorMother.identityNotFoundError());
    }

    @Test
    void shouldThrowExceptionIfInternalServerErrorReturnedFromCreateContext() {
        FacadeCreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        ContextRequestHeaders headers = ContextRequestHeaders.build(createRequest.getChannelId());
        ClientCreateContextRequest request = ClientCreateContextRequest.builder()
                .body(createRequest)
                .headers(headers)
                .build();

        SERVER.stubFor(configureUpdateHeaders(post(urlEqualTo(CREATE_CONTEXT_URL)), headers)
                .withRequestBody(equalTo(JSON_CONVERTER.toJson(createRequest)))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody(InternalServerErrorJsonMother.internalServerErrorJson())));

        ApiErrorClientException error = catchThrowableOfType(
                () -> client.createContext(request),
                ApiErrorClientException.class
        );

        assertThat(error.getError())
                .usingRecursiveComparison()
                .isEqualTo(InternalServerErrorMother.internalServerError());
    }

    @Test
    void shouldReturnCreatedContextFromSuccessfulCreateContext() {
        FacadeCreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        ContextRequestHeaders headers = ContextRequestHeaders.build(createRequest.getChannelId());
        ClientCreateContextRequest request = ClientCreateContextRequest.builder()
                .body(createRequest)
                .headers(headers)
                .build();

        SERVER.stubFor(configureUpdateHeaders(post(urlEqualTo(CREATE_CONTEXT_URL)), headers)
                .withRequestBody(equalTo(JSON_CONVERTER.toJson(createRequest)))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withBody(ContextJsonMother.build())));

        Context context = client.createContext(request);

        assertThat(context).isEqualTo(ContextMother.build());
    }

    @Test
    void shouldThrowExceptionIfContextNotFoundErrorReturnedFromGetContext() {
        UUID id = UUID.randomUUID();
        ContextRequestHeaders headers = ContextRequestHeaders.build("abc");
        ClientGetContextRequest request = ClientGetContextRequest.builder()
                .id(id)
                .headers(headers)
                .build();

        SERVER.stubFor(configureGetHeaders(get(urlEqualTo(toGetUrl(id))), headers)
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody(ContextNotFoundErrorJsonMother.contextNotFoundErrorJson())));

        ApiErrorClientException error = catchThrowableOfType(
                () -> client.getContext(request),
                ApiErrorClientException.class
        );

        assertThat(error.getError())
                .usingRecursiveComparison()
                .isEqualTo(ContextNotFoundErrorMother.contextNotFoundError());
    }

    @Test
    void shouldThrowExceptionIfContextExpiredErrorReturnedFromGetContext() {
        UUID id = UUID.randomUUID();
        ContextRequestHeaders headers = ContextRequestHeaders.build("abc");
        ClientGetContextRequest request = ClientGetContextRequest.builder()
                .id(id)
                .headers(headers)
                .build();

        SERVER.stubFor(configureGetHeaders(get(urlEqualTo(toGetUrl(id))), headers)
                .willReturn(aResponse()
                        .withStatus(410)
                        .withBody(ContextExpiredErrorJsonMother.contextExpiredErrorJson())));

        ApiErrorClientException error = catchThrowableOfType(
                () -> client.getContext(request),
                ApiErrorClientException.class
        );

        assertThat(error.getError())
                .usingRecursiveComparison()
                .isEqualTo(ContextExpiredErrorMother.contextExpiredError());
    }

    @Test
    void shouldReturnContextFromSuccessfulGetContext() {
        UUID id = UUID.randomUUID();
        ContextRequestHeaders headers = ContextRequestHeaders.build("abc");
        ClientGetContextRequest request = ClientGetContextRequest.builder()
                .id(id)
                .headers(headers)
                .build();

        SERVER.stubFor(configureGetHeaders(get(urlEqualTo(toGetUrl(id))), headers)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(ContextJsonMother.build())));

        Context context = client.getContext(request);

        assertThat(context).isEqualTo(ContextMother.build());
    }

    @Test
    void shouldThrowExceptionIfContextNotFoundErrorReturnedFromCreateVerification() {
        CreateVerificationRequest createRequest = CreateVerificationRequestMother.build();
        ContextRequestHeaders headers = ContextRequestHeaders.build("abc");
        ClientCreateVerificationRequest request = ClientCreateVerificationRequest.builder()
                .body(createRequest)
                .headers(headers)
                .build();

        SERVER.stubFor(configureUpdateHeaders(patch(urlEqualTo(toCreateVerificationUrl(createRequest.getContextId()))), headers)
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody(ContextNotFoundErrorJsonMother.contextNotFoundErrorJson())));

        ApiErrorClientException error = catchThrowableOfType(
                () -> client.createVerification(request),
                ApiErrorClientException.class
        );

        assertThat(error.getError())
                .usingRecursiveComparison()
                .isEqualTo(ContextNotFoundErrorMother.contextNotFoundError());
    }

    @Test
    void shouldThrowExceptionIfContextExpiredErrorReturnedFromCreateVerification() {
        CreateVerificationRequest createRequest = CreateVerificationRequestMother.build();
        ContextRequestHeaders headers = ContextRequestHeaders.build("abc");
        ClientCreateVerificationRequest request = ClientCreateVerificationRequest.builder()
                .body(createRequest)
                .headers(headers)
                .build();

        SERVER.stubFor(configureUpdateHeaders(patch(urlEqualTo(toCreateVerificationUrl(createRequest.getContextId()))), headers)
                .willReturn(aResponse()
                        .withStatus(410)
                        .withBody(ContextExpiredErrorJsonMother.contextExpiredErrorJson())));

        ApiErrorClientException error = catchThrowableOfType(
                () -> client.createVerification(request),
                ApiErrorClientException.class
        );

        assertThat(error.getError())
                .usingRecursiveComparison()
                .isEqualTo(ContextExpiredErrorMother.contextExpiredError());
    }

    @Test
    void shouldThrowExceptionIfNotNextMethodReturnedFromCreateVerification() {
        CreateVerificationRequest createRequest = CreateVerificationRequestMother.builder()
                .methodName("default-method")
                .build();
        ContextRequestHeaders headers = ContextRequestHeaders.build("abc");
        ClientCreateVerificationRequest request = ClientCreateVerificationRequest.builder()
                .body(createRequest)
                .headers(headers)
                .build();

        SERVER.stubFor(configureUpdateHeaders(patch(urlEqualTo(toCreateVerificationUrl(createRequest.getContextId()))), headers)
                .willReturn(aResponse()
                        .withStatus(422)
                        .withBody(NotNextMethodErrorJsonMother.notNextMethodErrorJson())));

        ApiErrorClientException error = catchThrowableOfType(
                () -> client.createVerification(request),
                ApiErrorClientException.class
        );

        assertThat(error.getError())
                .usingRecursiveComparison()
                .isEqualTo(NotNextMethodErrorMother.notNextMethodError());
    }

    @Test
    void shouldReturnVerificationFromSuccessfulCreateVerification() {
        CreateVerificationRequest createRequest = CreateVerificationRequestMother.build();
        ContextRequestHeaders headers = ContextRequestHeaders.build("abc");
        ClientCreateVerificationRequest request = ClientCreateVerificationRequest.builder()
                .body(createRequest)
                .headers(headers)
                .build();

        SERVER.stubFor(configureUpdateHeaders(patch(urlEqualTo(toCreateVerificationUrl(createRequest.getContextId()))), headers)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(VerificationJsonMother.incomplete())));

        Verification verification = client.createVerification(request);

        assertThat(verification).isEqualTo(VerificationMother.incomplete());
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

    private static String toGetUrl(UUID id) {
        return String.format(GET_CONTEXT_URL, id.toString());
    }

    private static String toCreateVerificationUrl(UUID id) {
        return String.format(CREATE_VERIFICATION_URL, id.toString());
    }

}
