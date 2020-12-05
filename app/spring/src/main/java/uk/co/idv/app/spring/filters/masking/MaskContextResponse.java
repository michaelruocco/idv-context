package uk.co.idv.app.spring.filters.masking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.json.context.mask.ContextJsonMasker;
import uk.co.mruoc.spring.filter.rewrite.RewriteResponseBody;
import uk.co.mruoc.spring.filter.rewrite.RewriteResponseBodyRequest;

import java.util.function.UnaryOperator;

@RequiredArgsConstructor
@Slf4j
public class MaskContextResponse implements RewriteResponseBody {

    private static final Configuration DEFAULT_CONFIG = Configuration.defaultConfiguration()
            .addOptions(Option.SUPPRESS_EXCEPTIONS);

    private final Configuration config;
    private final UnaryOperator<String> masker;

    public MaskContextResponse(ObjectMapper mapper) {
        this(DEFAULT_CONFIG, new ContextJsonMasker(mapper));
    }

    @Override
    public String apply(RewriteResponseBodyRequest rewriteRequest) {
        return maskNumbersIfRequired(rewriteRequest);
    }

    private String maskNumbersIfRequired(RewriteResponseBodyRequest rewriteRequest) {
        String body = rewriteRequest.getResponseBody();
        if (shouldMaskNumbers(rewriteRequest)) {
            return masker.apply(body);
        }
        return body;
    }

    private boolean shouldMaskNumbers(RewriteResponseBodyRequest rewriteRequest) {
        return rewriteRequest.isPostRequest() &&
                rewriteRequest.has2xxStatus() &&
                extractMaskSensitiveData(rewriteRequest.getResponseBody());
    }

    private boolean extractMaskSensitiveData(String body) {
        boolean maskSensitiveData = JsonPath.using(config)
                .parse(body)
                .read("$.request.policy.maskSensitiveData");
        log.info("mask sensitive data is {}", maskSensitiveData);
        return maskSensitiveData;
    }

}
