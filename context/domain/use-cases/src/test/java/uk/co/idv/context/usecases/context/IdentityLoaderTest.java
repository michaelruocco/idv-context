package uk.co.idv.context.usecases.context;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.context.create.ContextCreateEligibilityRequest;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.DefaultCreateContextRequestMother;
import uk.co.idv.context.entities.context.create.IdentityCreateContextRequest;
import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.entities.eligibility.EligibilityMother;
import uk.co.idv.context.entities.identity.FindIdentityRequest;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.usecases.eligibility.CreateEligibility;
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
        CreateContextRequest initialRequest = DefaultCreateContextRequestMother.build();
        givenEligibilityReturned();

        loader.addIdentity(initialRequest);

        ArgumentCaptor<ContextCreateEligibilityRequest> captor = ArgumentCaptor.forClass(ContextCreateEligibilityRequest.class);
        verify(createEligibility).create(captor.capture());
        ContextCreateEligibilityRequest eligibilityRequest = captor.getValue();
        assertThat(eligibilityRequest.getRequest()).isEqualTo(initialRequest);
    }

    @Test
    void shouldPassPolicyWhenCreatingEligibility() {
        CreateContextRequest initialRequest = DefaultCreateContextRequestMother.build();
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
        CreateContextRequest initialRequest = DefaultCreateContextRequestMother.build();
        givenEligibilityReturned();

        IdentityCreateContextRequest identityRequest = loader.addIdentity(initialRequest);

        assertThat(identityRequest.getInitial()).isEqualTo(initialRequest);
    }

    @Test
    void shouldReturnPolicy() {
        CreateContextRequest request = DefaultCreateContextRequestMother.build();
        ContextPolicy policy = givenPolicyLoadedForRequest(request);
        givenEligibilityReturned();

        IdentityCreateContextRequest identityRequest = loader.addIdentity(request);

        assertThat(identityRequest.getPolicy()).isEqualTo(policy);
    }

    @Test
    void shouldReturnIdentityFromEligibility() {
        CreateContextRequest request = DefaultCreateContextRequestMother.build();
        Eligibility eligibility = givenEligibilityReturned();

        IdentityCreateContextRequest identityRequest = loader.addIdentity(request);

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
