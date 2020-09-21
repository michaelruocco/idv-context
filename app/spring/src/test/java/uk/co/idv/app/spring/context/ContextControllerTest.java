package uk.co.idv.app.spring.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.context.usecases.context.ContextFacade;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ContextControllerTest {

    private final ContextFacade facade = mock(ContextFacade.class);

    private final ContextController controller = new ContextController(facade);

    @Test
    void shouldCreateContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        Context expectedContext = givenContextCreatedFor(request);

        Context context = controller.createContext(request);

        assertThat(context).isEqualTo(expectedContext);
    }

    @Test
    void shouldGetContext() {
        UUID id = UUID.randomUUID();
        Context expectedContext = givenContextLoadedFor(id);

        Context context = controller.getContext(id);

        assertThat(context).isEqualTo(expectedContext);
    }

    private Context givenContextCreatedFor(CreateContextRequest request) {
        Context context = ContextMother.build();
        given(facade.create(request)).willReturn(context);
        return context;
    }

    private Context givenContextLoadedFor(UUID id) {
        Context context = ContextMother.build();
        given(facade.find(id)).willReturn(context);
        return context;
    }

}
