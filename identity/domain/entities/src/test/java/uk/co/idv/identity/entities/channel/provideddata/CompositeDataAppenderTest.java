package uk.co.idv.identity.entities.channel.provideddata;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.identity.Identity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CompositeDataAppenderTest {

    @Test
    void shouldApplyAllAppendersToIdentity() {
        Identity identity = mock(Identity.class);
        Identity updated1 = mock(Identity.class);
        Identity updated2 = mock(Identity.class);
        DataAppender appender1 = mock(DataAppender.class);
        DataAppender appender2 = mock(DataAppender.class);
        given(appender1.apply(identity)).willReturn(updated1);
        given(appender2.apply(updated1)).willReturn(updated2);
        DataAppender composite = new CompositeDataAppender(appender1, appender2);

        Identity updated = composite.apply(identity);

        assertThat(updated).isEqualTo(updated2);
    }

}
