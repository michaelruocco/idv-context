package uk.co.idv.context.adapter.client;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import uk.co.idv.common.adapter.json.error.internalserver.InternalServerErrorJsonMother;
import uk.co.idv.common.adapter.json.error.internalserver.InternalServerErrorMother;
import uk.co.idv.context.adapter.client.exception.ApiErrorClientException;
import uk.co.idv.context.adapter.client.headers.ContextRequestHeaders;
import uk.co.idv.context.adapter.client.logger.DefaultClientLogger;
import uk.co.idv.context.adapter.client.request.ClientCreateContextRequest;
import uk.co.idv.context.adapter.client.request.ClientGetContextRequest;
import uk.co.idv.context.adapter.client.request.ClientRecordContextResultRequest;
import uk.co.idv.context.adapter.json.context.ContextJsonMother;
import uk.co.idv.context.adapter.json.context.error.contextexpired.ContextExpiredErrorJsonMother;
import uk.co.idv.context.adapter.json.context.error.contextexpired.ContextExpiredErrorMother;
import uk.co.idv.context.adapter.json.context.error.contextnotfound.ContextNotFoundErrorJsonMother;
import uk.co.idv.context.adapter.json.context.error.contextnotfound.ContextNotFoundErrorMother;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.context.entities.result.FacadeRecordResultRequest;
import uk.co.idv.context.entities.result.FacadeRecordResultRequestMother;
import uk.co.idv.identity.adapter.json.error.identitynotfound.IdentityNotFoundErrorJsonMother;
import uk.co.idv.identity.adapter.json.error.identitynotfound.IdentityNotFoundErrorMother;
import uk.co.mruoc.json.JsonConverter;

import java.net.http.HttpClient;
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

class RestContextClientIntegrationTest {

    @RegisterExtension
    public static final WireMockExtension SERVER = new WireMockExtension();

    private static final String CREATE_CONTEXT_URL = "/v1/contexts";
    private static final String GET_CONTEXT_URL = CREATE_CONTEXT_URL + "/%s";
    private static final String RECORD_CONTEXT_RESULT_URL = CREATE_CONTEXT_URL + "/results";

    private static final JsonConverter JSON_CONVERTER = JsonConverterFactory.build();
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    private RestContextClient client;

    @BeforeEach
    public void setUp() {
        client = RestContextClient.builder()
                .jsonConverter(JSON_CONVERTER)
                .client(HTTP_CLIENT)
                .baseUrl(SERVER.getBaseUrl())
                .logger(new DefaultClientLogger())
                .build();
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

    //TODO test for context not found
    //TODO test for context expired
    //TODO test for method not next in sequence, needs handling server side
    //TODO test for method not eligible, needs handling server side

    @Test
    void shouldReturnContextFromSuccessfulRecordContextResult() {
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.build();
        ContextRequestHeaders headers = ContextRequestHeaders.build("abc");
        ClientRecordContextResultRequest request = ClientRecordContextResultRequest.builder()
                .body(recordRequest)
                .headers(headers)
                .build();

        SERVER.stubFor(configureUpdateHeaders(patch(urlEqualTo(RECORD_CONTEXT_RESULT_URL)), headers)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(ContextJsonMother.build())));

        Context context = client.recordResult(request);

        assertThat(context).isEqualTo(ContextMother.build());
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

}
