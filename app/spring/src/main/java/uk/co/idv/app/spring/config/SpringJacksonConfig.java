package uk.co.idv.app.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.context.adapter.json.VerificationContextParentModule;
import uk.co.mruoc.json.JsonConverter;
import uk.co.mruoc.json.jackson.JacksonJsonConverter;

@Configuration
public class SpringJacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new VerificationContextParentModule());
    }

    @Bean
    public JsonConverter mapper(ObjectMapper mapper) {
        return new JacksonJsonConverter(mapper);
    }

}
