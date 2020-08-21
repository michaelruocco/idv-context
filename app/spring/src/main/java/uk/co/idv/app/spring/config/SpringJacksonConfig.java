package uk.co.idv.app.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.context.adapter.json.IdentityParentModule;
import uk.co.idv.context.adapter.json.lockout.LockoutParentModule;
import uk.co.mruoc.json.JsonConverter;
import uk.co.mruoc.json.jackson.JacksonJsonConverter;

@Configuration
public class SpringJacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModules(
                new JavaTimeModule(),
                new IdentityParentModule(),
                new LockoutParentModule()
        ).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Bean
    public JsonConverter mapper(ObjectMapper mapper) {
        return new JacksonJsonConverter(mapper);
    }

}
