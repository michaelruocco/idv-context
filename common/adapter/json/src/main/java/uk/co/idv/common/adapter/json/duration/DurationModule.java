package uk.co.idv.common.adapter.json.duration;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.Duration;

public class DurationModule extends SimpleModule {

    public DurationModule() {
        super("duration-module", Version.unknownVersion());

        addDeserializer(Duration.class, new DurationDeserializer());
        addSerializer(Duration.class, new DurationSerializer());
    }

}
