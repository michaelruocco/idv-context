package uk.co.idv.context.adapter.verification.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.verification.client.logger.BodyLoggingClientLogger;
import uk.co.idv.context.adapter.verification.client.logger.ClientLogger;
import uk.co.idv.context.adapter.verification.client.logger.MdcPopulator;
import uk.co.idv.context.adapter.verification.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequest;
import uk.co.idv.context.adapter.verification.client.request.RequestConverter;
import uk.co.idv.method.adapter.json.verification.mask.VerificationJsonMasker;
import uk.co.idv.method.entities.verification.CompleteVerificationResult;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.mruoc.json.jackson.JacksonJsonConverter;
import uk.co.mruoc.string.transform.UuidIdStringTransformer;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Builder
@Slf4j
public class RestVerificationClient implements VerificationClient {

    private final RequestConverter requestConverter;
    private final ResponseConverter responseConverter;
    private final RequestExecutor executor;

    public static VerificationClient build(VerificationClientConfig config) {
        return RestVerificationClient.builder()
                .requestConverter(toRequestConverter(config))
                .responseConverter(toResponseConverter(config.getMapper()))
                .executor(toRequestExecutor(config.getMapper()))
                .build();
    }

    @Override
    public Verification createVerification(ClientCreateVerificationRequest request) {
        HttpRequest httpRequest = requestConverter.toPostVerificationHttpRequest(request);
        HttpResponse<String> httpResponse = executor.execute(httpRequest);
        return responseConverter.toVerificationOrThrowError(httpResponse);
    }

    @Override
    public CompleteVerificationResult completeVerification(ClientCompleteVerificationRequest request) {
        HttpRequest httpRequest = requestConverter.toPatchVerificationHttpRequest(request);
        HttpResponse<String> httpResponse = executor.execute(httpRequest);
        return responseConverter.toCompleteVerificationResultOrThrowError(httpResponse);
    }

    private static RequestConverter toRequestConverter(VerificationClientConfig config) {
        return RequestConverter.builder()
                .jsonConverter(new JacksonJsonConverter(config.getMapper()))
                .baseUri(config.getBaseUri())
                .build();
    }

    private static ResponseConverter toResponseConverter(ObjectMapper mapper) {
        return new ResponseConverter(new JacksonJsonConverter(mapper));
    }

    private static RequestExecutor toRequestExecutor(ObjectMapper mapper) {
        return RequestExecutor.builder()
                .client(HttpClient.newHttpClient())
                .clientLogger(toClientLogger(mapper))
                .build();
    }

    private static ClientLogger toClientLogger(ObjectMapper mapper) {
        return BodyLoggingClientLogger.builder()
                .mdcPopulator(new MdcPopulator(new UuidIdStringTransformer()))
                .requestMasker(s -> s)
                .responseMasker(new VerificationJsonMasker(mapper))
                .build();
    }

}
