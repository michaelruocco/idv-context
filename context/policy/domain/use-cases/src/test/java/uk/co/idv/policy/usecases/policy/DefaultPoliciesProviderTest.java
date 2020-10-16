package uk.co.idv.policy.usecases.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.mruoc.file.content.FileContentLoader;
import uk.co.mruoc.json.JsonConverter;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DefaultPoliciesProviderTest {

    private static final String PATH1 = "path1";
    private static final String PATH2 = "path2";

    private final JsonConverter converter = mock(JsonConverter.class);
    private final Class<Policy> type = Policy.class;
    private final Collection<String> paths = Arrays.asList(PATH1, PATH2);
    private final FileContentLoader contentLoader = mock(FileContentLoader.class);

    private final DefaultPoliciesProvider<Policy> provider = new DefaultPoliciesProvider<>(
            converter, type, paths, contentLoader
    );

    @Test
    void shouldLoadPolicies() {
        Policy expectedPolicy1 = givenPolicyLoadedFromPath(PATH1);
        Policy expectedPolicy2 = givenPolicyLoadedFromPath(PATH2);

        Policies<Policy> policies = provider.getPolicies();

        assertThat(policies).containsExactly(
                expectedPolicy1,
                expectedPolicy2
        );
    }

    private Policy givenPolicyLoadedFromPath(String path) {
        String json = String.format("{%s}", path);
        given(contentLoader.loadContent(path)).willReturn(json);
        Policy policy = mock(Policy.class);
        given(converter.toObject(json, type)).willReturn(policy);
        return policy;
    }

}
