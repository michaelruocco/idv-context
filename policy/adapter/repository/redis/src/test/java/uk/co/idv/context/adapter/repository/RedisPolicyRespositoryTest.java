package uk.co.idv.context.adapter.repository;

import org.junit.jupiter.api.Test;
import uk.co.idv.policy.entities.policy.MockPolicyMother;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.mruoc.json.JsonConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class RedisPolicyRespositoryTest {

    private final Class<Policy> type = Policy.class;
    private final JsonConverter converter = mock(JsonConverter.class);
    private final Map<UUID, String> policies = new HashMap<>();

    private final RedisPolicyRespository<Policy> repository = new RedisPolicyRespository<>(type, converter, policies);

    @Test
    void shouldSavePolicy() {
        Policy policy = MockPolicyMother.policy();
        String json = givenPolicyWillBeConvertedTo(policy);

        repository.save(policy);

        assertThat(policies).contains(
                entry(policy.getId(), json)
        );
    }

    @Test
    void shouldLoadEmptyOptionalIfPolicyDoesNotExist() {
        UUID id = UUID.randomUUID();

        Optional<Policy> policy = repository.load(id);

        assertThat(policy).isEmpty();
    }

    @Test
    void shouldLoadPolicyIfPolicyExists() {
        String json = "policy-json";
        Policy expectedPolicy = givenJsonWillBeConvertedTo(json);
        policies.put(expectedPolicy.getId(), json);

        Optional<Policy> policy = repository.load(expectedPolicy.getId());

        assertThat(policy).contains(expectedPolicy);
    }

    @Test
    void shouldLoadAllPolicies() {
        String json1 = "policy-json-1";
        Policy expectedPolicy1 = givenJsonWillBeConvertedTo(json1);
        policies.put(expectedPolicy1.getId(), json1);

        String json2 = "policy-json-2";
        Policy expectedPolicy2 = givenJsonWillBeConvertedTo(json2);
        policies.put(expectedPolicy2.getId(), json2);

        Policies<Policy> loadedPolicies = repository.loadAll();

        assertThat(loadedPolicies).contains(
                expectedPolicy1,
                expectedPolicy2
        );
    }

    @Test
    void shouldDeletePolicy() {
        UUID id = UUID.randomUUID();
        policies.put(id, "policy-json");

        repository.delete(id);

        assertThat(policies).isEmpty();
    }

    private String givenPolicyWillBeConvertedTo(Policy policy) {
        String json = "policy-json";
        given(converter.toJson(policy)).willReturn(json);
        return json;
    }

    private Policy givenJsonWillBeConvertedTo(String json) {
        Policy policy = MockPolicyMother.withId(UUID.randomUUID());
        given(converter.toObject(json, type)).willReturn(policy);
        return policy;
    }

}
