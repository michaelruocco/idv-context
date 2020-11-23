package uk.co.idv.app.spring.filters.masking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import uk.co.idv.app.spring.filters.masking.phonenumber.ContextPhoneNumberResponseJsonMasker;
import uk.co.mruoc.json.mask.JsonMasker;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
public class MaskContextResponse implements Function<RewriteBodyRequest, String> {

    private static final Configuration DEFAULT_CONFIG = Configuration.defaultConfiguration()
            .addOptions(Option.SUPPRESS_EXCEPTIONS);

    private final Configuration config;
    private final JsonMasker masker;

    public MaskContextResponse(ObjectMapper mapper) {
        this(DEFAULT_CONFIG, new ContextPhoneNumberResponseJsonMasker(mapper));
    }

    @Override
    public String apply(RewriteBodyRequest rewriteRequest) {
        return maskNumbersIfRequired(rewriteRequest);
    }

    private String maskNumbersIfRequired(RewriteBodyRequest rewriteRequest) {
        HttpServletRequest request = rewriteRequest.getRequest();
        RewriteResponseWrapper response = rewriteRequest.getResponse();
        String responseBody = response.getBodyAsString();
        if (!isPost(request) || !is2xx(response)) {
            return responseBody;
        }
        boolean maskNumbers = extractMaskNumbers(responseBody);
        if (maskNumbers) {
            return masker.apply(responseBody);
        }
        return responseBody;
    }

    private boolean isPost(HttpServletRequest request) {
        boolean isPost = request.getMethod().equals("POST");
        log.info("request method is post {}", isPost);
        return isPost;
    }

    private boolean is2xx(RewriteResponseWrapper response) {
        boolean is2xx = response.has2xxStatus();
        log.info("response status is 2xx {}", is2xx);
        return is2xx;
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
