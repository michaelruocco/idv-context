package uk.co.idv.app.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.app.spring.time.OffsetClock;

import java.time.Clock;

@Configuration
public class TimeConfig {

    @Profile("!test")
    @Bean
    public Clock defaultClock() {
        return Clock.systemUTC();
    }

    @Profile("test")
    @Bean
    public OffsetClock testClock() {
        return new OffsetClock();
    }

}
