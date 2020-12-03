package uk.co.idv.app.spring.filters.masking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import uk.co.idv.context.adapter.json.context.mask.ContextPhoneNumberJsonMasker;
import uk.co.mruoc.json.mask.JsonMasker;
import uk.co.mruoc.spring.filter.rewrite.RewriteResponseBody;
import uk.co.mruoc.spring.filter.rewrite.RewriteResponseBodyRequest;

@RequiredArgsConstructor
@Slf4j
public class MaskContextResponse implements RewriteResponseBody {

    private static final Configuration DEFAULT_CONFIG = Configuration.defaultConfiguration()
            .addOptions(Option.SUPPRESS_EXCEPTIONS);

    private final Configuration config;
    private final JsonMasker masker;

    public MaskContextResponse(ObjectMapper mapper) {
        this(DEFAULT_CONFIG, new ContextPhoneNumberJsonMasker(mapper));
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
                extractMaskNumbers(rewriteRequest.getResponseBody());
    }

    private boolean extractMaskNumbers(String body) {
        JSONArray array = JsonPath.using(config)
                .parse(body)
                .read("$.request.policy.sequencePolicies[*].methodPolicies[?(@.name=='one-time-passcode')].deliveryMethodConfigs[*].phoneNumberConfig.maskNumbers");
        boolean maskNumbers = array.stream().anyMatch(value -> value.toString().equals("true"));
        log.info("mask numbers is {}", maskNumbers);
        return maskNumbers;
    }

}
