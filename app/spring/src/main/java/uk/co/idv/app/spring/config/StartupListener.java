package uk.co.idv.app.spring.config;

import lombok.Builder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import uk.co.idv.app.manual.Application;
import uk.co.idv.app.manual.adapter.channel.ChannelAdapter;

@Builder
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private final Application application;
    private final ChannelAdapter channelAdapter;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        application.populatePolicies(channelAdapter);
    }

}
