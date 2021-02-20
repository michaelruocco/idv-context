package uk.co.idv.app.spring.config.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.app.plain.config.JsonConfig;
import uk.co.idv.app.plain.adapter.channel.ChannelAdapter;
import uk.co.idv.method.adapter.json.method.MethodMappings;
import uk.co.idv.method.adapter.json.otp.OtpMapping;
import uk.co.mruoc.json.JsonConverter;

import java.time.Clock;

@Configuration
public class SpringJacksonConfig {

    @Bean
    public JsonConfig jsonConfig(Clock clock) {
        return new JsonConfig(clock, new MethodMappings(new OtpMapping()));
    }

    @Bean
    @ConditionalOnProperty(value = "response.filtering.enabled", havingValue = "false", matchIfMissing = true)
    public ObjectMapper objectMapper(JsonConfig jsonConfig) {
        return jsonConfig.getObjectMapper();
    }

    @Bean
    public JsonConverter converter(JsonConfig jsonConfig) {
        return jsonConfig.getJsonConverter();
    }

    @Bean
    public ChannelAdapter channelAdapter(JsonConfig jsonConfig) {
        return jsonConfig.channelAdapter();
    }

}
