package uk.co.idv.app.spring.config.repository;

import lombok.Builder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import uk.co.idv.app.plain.Application;
import uk.co.idv.app.plain.adapter.channel.ChannelAdapter;

@Builder
public class StubbedSetupPolicies implements ApplicationListener<ContextRefreshedEvent> {

    private final Application application;
    private final ChannelAdapter channelAdapter;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        application.populatePolicies(channelAdapter);
    }

}
