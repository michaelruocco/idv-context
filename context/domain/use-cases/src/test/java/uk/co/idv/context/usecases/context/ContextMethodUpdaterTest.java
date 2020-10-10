package uk.co.idv.context.usecases.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.entities.method.Method;

import java.util.Optional;
import java.util.UUID;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ContextMethodUpdaterTest {

    private final ContextRepository repository = mock(ContextRepository.class);

    private final ContextMethodUpdater updater = new ContextMethodUpdater(repository);

    @Test
    void shouldThrowExceptionIfContextNotFound() {
        UUID contextId = UUID.fromString("6c9fe290-51ed-4f13-bb71-18860ca39432");
        given(repository.load(contextId)).willReturn(Optional.empty());
        UnaryOperator<Method> function = mock(UnaryOperator.class);

        Throwable error = catchThrowable(() -> updater.update(contextId, function));

        assertThat(error)
                .isInstanceOf(ContextNotFoundException.class)
                .hasMessage(contextId.toString());
    }

    @Test
    void shouldUpdateContextUsingFunction() {
        UUID contextId = UUID.fromString("6c9fe290-51ed-4f13-bb71-18860ca39432");
        Context original = mock(Context.class);
        given(repository.load(contextId)).willReturn(Optional.of(original));
        UnaryOperator<Method> function = mock(UnaryOperator.class);
        Context expectedUpdated = mock(Context.class);
        given(original.updateMethods(function)).willReturn(expectedUpdated);

        Context updated = updater.update(contextId, function);

        assertThat(updated).isEqualTo(expectedUpdated);
        verify(repository).save(updated);
    }

}
