package uk.co.idv.app.spring.config;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.event.ContextRefreshedEvent;
import uk.co.idv.app.plain.Application;
import uk.co.idv.app.plain.adapter.channel.ChannelAdapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PopulatePoliciesTest {

    private final Application application = mock(Application.class);
    private final ChannelAdapter channelAdapter = mock(ChannelAdapter.class);

    private final PopulatePolicies populatePolicies = PopulatePolicies.builder()
            .application(application)
            .channelAdapter(channelAdapter)
            .build();

    @Test
    void shouldReturnSecondStartUpOrder() {
        assertThat(populatePolicies.getOrder()).isEqualTo(StartupListenerOrder.SECOND);
    }

    @Test
    void shouldPassChannelAdapterToPopulatePolicies() {
        ContextRefreshedEvent event = mock(ContextRefreshedEvent.class);

        populatePolicies.onApplicationEvent(event);

        ArgumentCaptor<ChannelAdapter> captor = ArgumentCaptor.forClass(ChannelAdapter.class);
        verify(application).populatePolicies(captor.capture());
        assertThat(captor.getValue()).isEqualTo(channelAdapter);
    }

}
