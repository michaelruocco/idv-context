package uk.co.idv.app.spring.lockout;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.idv.app.manual.Application;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.soft.SoftLockoutPolicyMother;
import uk.co.idv.policy.entities.policy.DefaultPolicyRequest;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.PolicyRequest;

import java.util.Collections;
import java.util.UUID;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LockoutPolicyControllerTest {

    private final Application application = mock(Application.class);

    private final LockoutPolicyController controller = new LockoutPolicyController(application);

    @Test
    void shouldGetPolicyById() {
        UUID id = UUID.randomUUID();
        LockoutPolicy expectedPolicy = SoftLockoutPolicyMother.build();
        given(application.loadLockoutPolicy(id)).willReturn(expectedPolicy);

        LockoutPolicy policy = controller.getPolicy(id);

        assertThat(policy).isEqualTo(expectedPolicy);
    }

    @Test
    void shouldPopulateArgumentsOntoPolicyRequest() {
        String channelId = "my-channel";
        String activityName = "my-activity";
        String aliasType = "my-alias";

        controller.getPolicies(channelId, activityName, aliasType);

        ArgumentCaptor<DefaultPolicyRequest> request = ArgumentCaptor.forClass(DefaultPolicyRequest.class);
        verify(application).loadLockoutPolicies(request.capture());
        assertThat(request.getValue())
                .hasFieldOrPropertyWithValue("channelId", channelId)
                .hasFieldOrPropertyWithValue("activityName", activityName)
                .hasFieldOrPropertyWithValue("aliasTypes", Collections.singleton(aliasType));
    }

    @Test
    void shouldGetAllPoliciesByPolicyRequestIfAtLeastOneArgumentProvided() {
        String channelId = "my-channel";
        Policies<LockoutPolicy> expectedPolicies = new Policies<>();
        given(application.loadLockoutPolicies(any(PolicyRequest.class))).willReturn(expectedPolicies);

        Policies<LockoutPolicy> policies = controller.getPolicies(channelId, null, null);

        assertThat(policies).isEqualTo(expectedPolicies);
    }

    @Test
    void shouldCreatePolicy() {
        LockoutPolicy created = SoftLockoutPolicyMother.build();
        LockoutPolicy expected = mock(LockoutPolicy.class);
        given(application.loadLockoutPolicy(created.getId())).willReturn(expected);

        ResponseEntity<LockoutPolicy> response = controller.create(created);

        verify(application).create(created);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void shouldReturnLocationForCreatedPolicy() {
        LockoutPolicy policy = SoftLockoutPolicyMother.build();

        ResponseEntity<LockoutPolicy> response = controller.create(policy);

        String expectedLocation = String.format("/v1/lockout-policies/%s", policy.getId());
        assertThat(response.getHeaders()).contains(
                entry("Location", singletonList(expectedLocation))
        );
    }

    @Test
    void shouldUpdatePolicy() {
        LockoutPolicy policy = SoftLockoutPolicyMother.build();
        LockoutPolicy expected = mock(LockoutPolicy.class);
        given(application.loadLockoutPolicy(policy.getId())).willReturn(expected);

        LockoutPolicy updated = controller.update(policy);

        verify(application).update(policy);
        assertThat(updated).isEqualTo(expected);
    }

    @Test
    void shouldDeletePolicyById() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Object> response = controller.delete(id);

        verify(application).deleteLockoutPolicy(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
    }

}
