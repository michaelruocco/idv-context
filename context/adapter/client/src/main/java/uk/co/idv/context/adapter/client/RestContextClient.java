package uk.co.idv.context.adapter.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.client.logger.BodyLoggingClientLogger;
import uk.co.idv.context.adapter.client.logger.ClientLogger;
import uk.co.idv.context.adapter.client.logger.MdcPopulator;
import uk.co.idv.context.adapter.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.context.adapter.client.request.ClientCreateContextRequest;
import uk.co.idv.context.adapter.client.request.ClientGetContextRequest;
import uk.co.idv.context.adapter.client.request.ClientCreateVerificationRequest;
import uk.co.idv.context.adapter.client.request.RequestConverter;
import uk.co.idv.context.adapter.json.context.create.mask.FacadeCreateContextRequestJsonMasker;
import uk.co.idv.context.adapter.json.context.mask.ContextJsonMasker;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.mruoc.json.jackson.JacksonJsonConverter;
import uk.co.mruoc.string.transform.UuidIdStringTransformer;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Builder
@Slf4j
public class RestContextClient implements ContextClient {

    private final RequestConverter requestConverter;
    private final ResponseConverter responseConverter;
    private final RequestExecutor executor;

    public static ContextClient build(ContextClientConfig config) {
        return RestContextClient.builder()
                .requestConverter(toRequestConverter(config))
                .responseConverter(toResponseConverter(config.getMapper()))
                .executor(toRequestExecutor(config.getMapper()))
                .build();
    }

    @Override
    public Context createContext(ClientCreateContextRequest request) {
        HttpRequest httpRequest = requestConverter.toPostContextHttpRequest(request);
        HttpResponse<String> httpResponse = executor.execute(httpRequest);
        return responseConverter.toContextOrThrowError(httpResponse);
    }

    @Override
    public Context getContext(ClientGetContextRequest request) {
        HttpRequest httpRequest = requestConverter.toGetContextHttpRequest(request);
        HttpResponse<String> httpResponse = executor.execute(httpRequest);
        return responseConverter.toContextOrThrowError(httpResponse);
    }

    @Override
    public Verification createVerification(ClientCreateVerificationRequest request) {
        HttpRequest httpRequest = requestConverter.toPostVerificationHttpRequest(request);
        HttpResponse<String> httpResponse = executor.execute(httpRequest);
        return responseConverter.toVerificationOrThrowError(httpResponse);
    }

    @Override
    public Verification completeVerification(ClientCompleteVerificationRequest request) {
        HttpRequest httpRequest = requestConverter.toPatchVerificationHttpRequest(request);
        HttpResponse<String> httpResponse = executor.execute(httpRequest);
        return responseConverter.toVerificationOrThrowError(httpResponse);
    }

    private static RequestConverter toRequestConverter(ContextClientConfig config) {
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
                .requestMasker(new FacadeCreateContextRequestJsonMasker(mapper))
                .responseMasker(new ContextJsonMasker(mapper))
                .build();
    }

}
