package uk.co.idv.context.usecases.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequestMother;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ContextServiceTest {

    private final CreateContext createContext = mock(CreateContext.class);
    private final FindContext findContext = mock(FindContext.class);

    private final ContextService service = ContextService.builder()
            .createContext(createContext)
            .findContext(findContext)
            .build();

    @Test
    void shouldCreateContext() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();
        Context expectedContext = mock(Context.class);
        given(createContext.create(request)).willReturn(expectedContext);

        Context context = service.create(request);

        assertThat(context).isEqualTo(expectedContext);
    }

    @Test
    void shouldFindContext() {
        UUID id = UUID.randomUUID();
        Context expectedContext = mock(Context.class);
        given(findContext.find(id)).willReturn(expectedContext);

        Context context = service.find(id);

        assertThat(context).isEqualTo(expectedContext);
    }

    @Test
    void shouldFindContextWithEligibleAndIncompleteSequences() {
        UUID id = UUID.randomUUID();
        Context originalContext = mock(Context.class);
        given(findContext.find(id)).willReturn(originalContext);
        Context expectedContext = mock(Context.class);
        given(originalContext.withOnlyEligibleAndIncompleteSequences()).willReturn(expectedContext);

        Context context = service.findWithEligibleIncompleteMethods(id);

        assertThat(context).isEqualTo(expectedContext);
    }

}
