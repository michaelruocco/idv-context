package uk.co.idv.app.plain.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import uk.co.idv.app.plain.adapter.channel.ChannelAdapter;
import uk.co.idv.app.plain.adapter.channel.DefaultChannelAdapter;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.adapter.json.ContextParentModule;
import uk.co.idv.identity.adapter.json.IdentityParentModule;
import uk.co.idv.lockout.adapter.json.LockoutParentModule;
import uk.co.idv.method.adapter.json.method.MethodMappings;
import uk.co.mruoc.json.JsonConverter;
import uk.co.mruoc.json.jackson.JacksonJsonConverter;

import java.time.Clock;

@Data
public class JsonConfig {

    private final ObjectMapper objectMapper;
    private final JsonConverter jsonConverter;

    public JsonConfig(Clock clock, MethodMappings mappings) {
        this.objectMapper = ObjectMapperFactory.build(
                new ContextParentModule(mappings),
                new IdentityParentModule(),
                new LockoutParentModule(clock)
        );
        this.jsonConverter = new JacksonJsonConverter(objectMapper);
    }

    public ChannelAdapter channelAdapter() {
        return new DefaultChannelAdapter(jsonConverter);
    }

}
