package uk.co.idv.identity.usecases.identity.merge;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.identity.entities.event.MergeIdentitiesEvent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CompositeMergeIdentitiesHandlerTest {

    @Test
    void shouldCallMergeOnEachHandler() {
        MergeIdentitiesHandler handler1 = mock(MergeIdentitiesHandler.class);
        MergeIdentitiesHandler handler2 = mock(MergeIdentitiesHandler.class);
        MergeIdentitiesHandler composite = new CompositeMergeIdentitiesHandler(handler1, handler2);
        MergeIdentitiesEvent event = mock(MergeIdentitiesEvent.class);

        composite.merged(event);

        ArgumentCaptor<MergeIdentitiesEvent> captor = ArgumentCaptor.forClass(MergeIdentitiesEvent.class);
        verify(handler1).merged(captor.capture());
        assertThat(captor.getValue()).isEqualTo(event);
        verify(handler2).merged(captor.capture());
        assertThat(captor.getValue()).isEqualTo(event);
    }

}
