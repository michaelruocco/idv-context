package uk.co.idv.context.usecases.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.DefaultCreateContextRequestMother;
import uk.co.idv.context.entities.policy.ContextPolicy;

import static org.assertj.core.api.Assertions.assertThat;

class ContextPolicyServiceTest {

    private final ContextPolicyService service = new ContextPolicyService();

    @Test
    void shouldReturnContextPolicy() {
        CreateContextRequest request = DefaultCreateContextRequestMother.build();

        ContextPolicy policy = service.load(request);

        assertThat(policy).isInstanceOf(ContextPolicy.class);
    }

}
