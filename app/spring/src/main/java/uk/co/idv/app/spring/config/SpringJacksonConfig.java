package uk.co.idv.app.spring.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.context.adapter.json.policy.ContextPolicyModule;
import uk.co.idv.identity.adapter.json.IdentityParentModule;
import uk.co.idv.lockout.adapter.json.LockoutParentModule;
import uk.co.mruoc.json.JsonConverter;
import uk.co.mruoc.json.jackson.JacksonJsonConverter;

@Configuration
public class SpringJacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .registerModules(
                        new JavaTimeModule(),
                        new IdentityParentModule(),
                        new LockoutParentModule(),
                        new ContextPolicyModule()
                );
    }

    @Bean
    public JsonConverter mapper(ObjectMapper mapper) {
        return new JacksonJsonConverter(mapper);
    }

}
