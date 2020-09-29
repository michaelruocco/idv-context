package uk.co.idv.context.usecases.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.usecases.context.identity.IdentityLoader;
import uk.co.idv.context.usecases.context.lockout.LockoutStateValidator;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ContextFacadeTest {

    private final IdentityLoader identityLoader = mock(IdentityLoader.class);
    private final LockoutStateValidator stateValidator = mock(LockoutStateValidator.class);
    private final ContextService contextService = mock(ContextService.class);

    private final ContextFacade facade = ContextFacade.builder()
            .identityLoader(identityLoader)
            .stateValidator(stateValidator)
            .contextService(contextService)
            .build();

    @Test
    void shouldCreateContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        ServiceCreateContextRequest identityRequest = givenIdentityRequestLoaded(request);
        Context expectedContext = givenContextCreated(identityRequest);

        Context context = facade.create(request);

        assertThat(context).isEqualTo(expectedContext);
    }

    @Test
    void shouldValidateLockoutState() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        ServiceCreateContextRequest identityRequest = givenIdentityRequestLoaded(request);

        facade.create(request);

        verify(stateValidator).validateLockoutState(identityRequest);
    }

    @Test
    void shouldFindContext() {
        UUID id = UUID.randomUUID();
        Context expectedContext = givenContextFound(id);

        Context context = facade.find(id);

        assertThat(context).isEqualTo(expectedContext);
    }

    private ServiceCreateContextRequest givenIdentityRequestLoaded(CreateContextRequest initialRequest) {
        ServiceCreateContextRequest identityRequest = mock(ServiceCreateContextRequest.class);
        given(identityLoader.addIdentity(initialRequest)).willReturn(identityRequest);
        return identityRequest;
    }

    private Context givenContextCreated(ServiceCreateContextRequest request) {
        Context context = mock(Context.class);
        given(contextService.create(request)).willReturn(context);
        return context;
    }

    private Context givenContextFound(UUID id) {
        Context context = mock(Context.class);
        given(contextService.find(id)).willReturn(context);
        return context;
    }

}
