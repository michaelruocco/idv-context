package uk.co.idv.context.adapter.verification.client.logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.MDC;
import uk.co.idv.context.adapter.verification.client.request.UriFactory;

import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static uk.co.idv.context.adapter.verification.client.logging.LogOutputUtils.captureLogLines;


class ClientBodyLoggerTest {

    private final UnaryOperator<String> uriTransformer = s -> s.replaceFirst("endpoint", "transformed");
    private final MdcPopulator mdcPopulator = new MdcPopulator(uriTransformer);
    private final UnaryOperator<String> requestMasker = s -> "masked-request";
    private final UnaryOperator<String> responseMasker = s -> "masked-response";

    private final ClientLogger logger = BodyLoggingClientLogger.builder()
            .mdcPopulator(mdcPopulator)
            .requestMasker(requestMasker)
            .responseMasker(responseMasker)
            .build();

    @AfterEach
    public void tearDown() {
        MDC.clear();
    }

    @Test
    void shouldLogRequestWithMdcHeaders() throws Exception {
        HttpRequest request = buildRequest();

        Collection<String> logLines = captureLogLines(() -> logger.log(request));

        assertThat(logLines).containsExactly("[http://localhost:8080/request-endpoint:" +
                "http://localhost:8080/request-transformed:" +
                "POST::] INFO  - sending-request:" +
                "uri:http://localhost:8080/request-endpoint:" +
                "headers:{request-header-key=[request-header-value]}:" +
                "body:masked-request");
    }

    @Test
    void shouldLogResponseWithMdcHeaders() throws Exception {
        HttpResponse<String> response = buildResponse();

        Collection<String> logLines = captureLogLines(() -> logger.log(response));

        assertThat(logLines).containsExactly("[:::" +
                "201:] INFO  - received-response:" +
                "status:201:" +
                "headers:{response-header-key=[response-header-value]}:" +
                "body:masked-response");
    }

    @Test
    void shouldLogDurationWithMdcHeaders() throws Exception {
        long duration = 1234;

        Collection<String> logLines = captureLogLines(() -> logger.logDuration(duration));

        assertThat(logLines).containsExactly("[::::1234] INFO  - client request took 1234 ms");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "client-request-uri",
            "client-request-uri-transformed",
            "client-request-method",
            "client-request-status",
            "client-request-status"
    })
    void shouldShouldClearMdcValueOnComplete(String key) {
        MDC.put(key, "value");

        logger.complete();

        assertThat(MDC.get(key)).isNull();
    }

    private static HttpRequest buildRequest() {
        return HttpRequest.newBuilder()
                .uri(UriFactory.toUri("http://localhost:8080/request-endpoint"))
                .header("request-header-key", "request-header-value")
                .POST(HttpRequest.BodyPublishers.ofString("request-body"))
                .build();
    }

    private static HttpResponse<String> buildResponse() {
        HttpResponse<String> response = mock(HttpResponse.class);
        given(response.statusCode()).willReturn(201);
        given(response.body()).willReturn("response-body");
        given(response.headers()).willReturn(buildHeaders());
        return response;
    }

    private static HttpHeaders buildHeaders() {
        BiPredicate<String, String> allowAll = (b, u) -> true;
        Map<String, List<String>> headerValues = Map.of("response-header-key", List.of("response-header-value"));
        return HttpHeaders.of(headerValues, allowAll);
    }

}
