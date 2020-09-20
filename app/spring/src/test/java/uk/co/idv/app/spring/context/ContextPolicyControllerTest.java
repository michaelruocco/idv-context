package uk.co.idv.app.spring.context;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.context.usecases.policy.ContextPolicyService;
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

class ContextPolicyControllerTest {

    private final ContextPolicyService service = mock(ContextPolicyService.class);

    private final ContextPolicyController controller = new ContextPolicyController(service);

    @Test
    void shouldGetPolicyById() {
        UUID id = UUID.randomUUID();
        ContextPolicy expectedPolicy = ContextPolicyMother.build();
        given(service.load(id)).willReturn(expectedPolicy);

        ContextPolicy policy = controller.getPolicy(id);

        assertThat(policy).isEqualTo(expectedPolicy);
    }

    @Test
    void shouldGetAllPoliciesIfNoArgumentsProvided() {
        Policies<ContextPolicy> expectedPolicies = new Policies<>();
        given(service.loadAll()).willReturn(expectedPolicies);

        Policies<ContextPolicy> policies = controller.getPolicies(null, null, null);

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
                .hasFieldOrPropertyWithValue("aliasTypes", Collections.singleton(aliasType));
    }

    @Test
    void shouldGetAllPoliciesByPolicyRequestIfAtLeastOneArgumentProvided() {
        String channelId = "my-channel";
        Policies<ContextPolicy> expectedPolicies = new Policies<>();
        given(service.load(any(PolicyRequest.class))).willReturn(expectedPolicies);

        Policies<ContextPolicy> policies = controller.getPolicies(channelId, null, null);

        assertThat(policies).isEqualTo(expectedPolicies);
    }

    @Test
    void shouldCreatePolicy() {
        ContextPolicy created = ContextPolicyMother.build();
        ContextPolicy expected = mock(ContextPolicy.class);
        given(service.load(created.getId())).willReturn(expected);

        ResponseEntity<ContextPolicy> response = controller.create(created);

        verify(service).create(created);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void shouldReturnLocationForCreatePolicy() {
        ContextPolicy policy = ContextPolicyMother.build();

        ResponseEntity<ContextPolicy> response = controller.create(policy);

        String expectedLocation = String.format("/context-policies/%s", policy.getId());
        assertThat(response.getHeaders()).contains(
                entry("Location", singletonList(expectedLocation))
        );
    }

    @Test
    void shouldUpdatePolicy() {
        ContextPolicy policy = ContextPolicyMother.build();
        ContextPolicy expected = mock(ContextPolicy.class);
        given(service.load(policy.getId())).willReturn(expected);

        ContextPolicy updated = controller.update(policy);

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
