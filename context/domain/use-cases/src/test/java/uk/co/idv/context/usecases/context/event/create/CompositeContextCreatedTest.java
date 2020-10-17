package uk.co.idv.context.usecases.context.event.create;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.context.Context;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CompositeContextCreatedTest {

    @Test
    void shouldCallCreatedOnEachHandler() {
        ContextCreatedHandler handler1 = mock(ContextCreatedHandler.class);
        ContextCreatedHandler handler2 = mock(ContextCreatedHandler.class);
        ContextCreatedHandler composite = new CompositeContextCreatedHandler(handler1, handler2);
        Context context = mock(Context.class);

        composite.created(context);

        ArgumentCaptor<Context> captor = ArgumentCaptor.forClass(Context.class);
        verify(handler1).created(captor.capture());
        assertThat(captor.getValue()).isEqualTo(context);
        verify(handler2).created(captor.capture());
        assertThat(captor.getValue()).isEqualTo(context);
    }

}
