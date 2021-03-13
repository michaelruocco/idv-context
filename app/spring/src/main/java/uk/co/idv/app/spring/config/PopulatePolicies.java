package uk.co.idv.app.spring.config;

import lombok.Builder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import uk.co.idv.app.plain.Application;
import uk.co.idv.app.plain.adapter.channel.ChannelAdapter;

@Builder
public class PopulatePolicies implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    private final Application application;
    private final ChannelAdapter channelAdapter;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        application.populatePolicies(channelAdapter);
    }

    @Override
    public int getOrder() {
        return StartupListenerOrder.SECOND;
    }

}
