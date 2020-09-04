package uk.co.idv.context.usecases.context;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.context.create.ContextCreateEligibilityRequest;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.context.entities.context.create.DefaultCreateContextRequest;
import uk.co.idv.identity.entities.eligibility.Eligibility;
import uk.co.idv.identity.entities.eligibility.EligibilityMother;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.identity.usecases.eligibility.CreateEligibility;
import uk.co.idv.context.usecases.policy.ContextPolicyService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class IdentityLoaderTest {

    private final ContextPolicyService policyService = mock(ContextPolicyService.class);
    private final CreateEligibility createEligibility = mock(CreateEligibility.class);

    private final IdentityLoader loader = IdentityLoader.builder()
            .policyService(policyService)
            .createEligibility(createEligibility)
            .build();

    @Test
    void shouldPassOriginalRequestWhenCreatingEligibility() {
        CreateContextRequest initialRequest = FacadeCreateContextRequestMother.build();
        givenEligibilityReturned();

        loader.addIdentity(initialRequest);

        ArgumentCaptor<ContextCreateEligibilityRequest> captor = ArgumentCaptor.forClass(ContextCreateEligibilityRequest.class);
        verify(createEligibility).create(captor.capture());
        ContextCreateEligibilityRequest eligibilityRequest = captor.getValue();
        assertThat(eligibilityRequest.getRequest()).isEqualTo(initialRequest);
    }

    @Test
    void shouldPassPolicyWhenCreatingEligibility() {
        CreateContextRequest initialRequest = FacadeCreateContextRequestMother.build();
        ContextPolicy policy = givenPolicyLoadedForRequest(initialRequest);
        givenEligibilityReturned();

        loader.addIdentity(initialRequest);

        ArgumentCaptor<ContextCreateEligibilityRequest> captor = ArgumentCaptor.forClass(ContextCreateEligibilityRequest.class);
        verify(createEligibility).create(captor.capture());
        ContextCreateEligibilityRequest eligibilityRequest = captor.getValue();
        assertThat(eligibilityRequest.getPolicy()).isEqualTo(policy);
    }

    @Test
    void shouldReturnInitialRequest() {
        CreateContextRequest initialRequest = FacadeCreateContextRequestMother.build();
        givenEligibilityReturned();

        DefaultCreateContextRequest identityRequest = loader.addIdentity(initialRequest);

        assertThat(identityRequest.getInitial()).isEqualTo(initialRequest);
    }

    @Test
    void shouldReturnPolicy() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        ContextPolicy policy = givenPolicyLoadedForRequest(request);
        givenEligibilityReturned();

        DefaultCreateContextRequest identityRequest = loader.addIdentity(request);

        assertThat(identityRequest.getPolicy()).isEqualTo(policy);
    }

    @Test
    void shouldReturnIdentityFromEligibility() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        Eligibility eligibility = givenEligibilityReturned();

        DefaultCreateContextRequest identityRequest = loader.addIdentity(request);

        assertThat(identityRequest.getIdentity()).isEqualTo(eligibility.getIdentity());
    }

    private ContextPolicy givenPolicyLoadedForRequest(CreateContextRequest request) {
        ContextPolicy policy = mock(ContextPolicy.class);
        given(policyService.load(request)).willReturn(policy);
        return policy;
    }

    private Eligibility givenEligibilityReturned() {
        Eligibility eligibility = EligibilityMother.build();
        given(createEligibility.create(any(FindIdentityRequest.class))).willReturn(eligibility);
        return eligibility;
    }

}
