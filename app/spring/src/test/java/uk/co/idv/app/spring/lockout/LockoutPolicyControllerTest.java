package uk.co.idv.app.spring.lockout;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.entities.lockout.policy.soft.SoftLockoutPolicyMother;
import uk.co.idv.context.entities.policy.DefaultPolicyRequest;
import uk.co.idv.context.entities.policy.Policies;
import uk.co.idv.context.entities.policy.PolicyRequest;
import uk.co.idv.context.usecases.lockout.LockoutPolicyService;

import java.util.UUID;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LockoutPolicyControllerTest {

    private final LockoutPolicyService service = mock(LockoutPolicyService.class);

    private final LockoutPolicyController controller = new LockoutPolicyController(service);

    @Test
    void shouldGetPolicyById() {
        UUID id = UUID.randomUUID();
        LockoutPolicy expectedPolicy = SoftLockoutPolicyMother.build();
        given(service.load(id)).willReturn(expectedPolicy);

        LockoutPolicy policy = controller.getPolicy(id);

        assertThat(policy).isEqualTo(expectedPolicy);
    }

    @Test
    void shouldGetAllPoliciesIfNoArgumentsProvided() {
        Policies<LockoutPolicy> expectedPolicies = new Policies<>();
        given(service.loadAll()).willReturn(expectedPolicies);

        Policies<LockoutPolicy> policies = controller.getPolicies(null, null, null);

        assertThat(policies).isEqualTo(expectedPolicies);
    }

    @Test
    void shouldPopulateArgumentsOntoPolicyRequest() {
        String channelId = "my-channel";
        String activityName = "my-activity";
        String aliasType = "my-alias";

        controller.getPolicies(channelId, activityName, aliasType);

        ArgumentCaptor<DefaultPolicyRequest> request = ArgumentCaptor.forClass(DefaultPolicyRequest.class);
        verify(service).load(request.capture());
        assertThat(request.getValue())
                .hasFieldOrPropertyWithValue("channelId", channelId)
                .hasFieldOrPropertyWithValue("activityName", activityName)
                .hasFieldOrPropertyWithValue("aliasType", aliasType);
    }

    @Test
    void shouldGetAllPoliciesByPolicyRequestIfAtLeastOneArgumentProvided() {
        String channelId = "my-channel";
        Policies<LockoutPolicy> expectedPolicies = new Policies<>();
        given(service.load(any(PolicyRequest.class))).willReturn(expectedPolicies);

        Policies<LockoutPolicy> policies = controller.getPolicies(channelId, null, null);

        assertThat(policies).isEqualTo(expectedPolicies);
    }

    @Test
    void shouldCreatePolicy() {
        LockoutPolicy created = SoftLockoutPolicyMother.build();
        LockoutPolicy expected = mock(LockoutPolicy.class);
        given(service.load(created.getId())).willReturn(expected);

        ResponseEntity<LockoutPolicy> response = controller.create(created);

        verify(service).create(created);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void shouldReturnLocationForCreatePolicy() {
        LockoutPolicy policy = SoftLockoutPolicyMother.build();

        ResponseEntity<LockoutPolicy> response = controller.create(policy);

        String expectedLocation = String.format("/lockout-policies/%s", policy.getId());
        assertThat(response.getHeaders()).contains(
                entry("Location", singletonList(expectedLocation))
        );
    }

    @Test
    void shouldUpdatePolicy() {
        LockoutPolicy policy = SoftLockoutPolicyMother.build();
        LockoutPolicy expected = mock(LockoutPolicy.class);
        given(service.load(policy.getId())).willReturn(expected);

        LockoutPolicy updated = controller.update(policy);

        verify(service).update(policy);
        assertThat(updated).isEqualTo(expected);
    }

    @Test
    void shouldDeletePolicyById() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Object> response = controller.delete(id);

        verify(service).delete(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
    }

}
