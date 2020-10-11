package uk.co.idv.app.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.mruoc.json.JsonConverter;
import uk.co.mruoc.json.jackson.JacksonJsonConverter;

@Configuration
public class SpringJacksonConfig {

    @Bean
    @ConditionalOnProperty(value = "response.filtering.enabled", havingValue = "false", matchIfMissing = true)
    public ObjectMapper objectMapper() {
        return SpringObjectMapperFactory.build();
    }

    @Bean
    public JsonConverter mapper(ObjectMapper mapper) {
        return new JacksonJsonConverter(mapper);
    }

}
