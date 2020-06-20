package uk.co.idv.context.usecases.eligibility.internal;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.entities.eligibility.EligibilityMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.eligibility.CreateEligibility;
import uk.co.idv.context.usecases.eligibility.CreateEligibilityRequest;
import uk.co.idv.context.usecases.eligibility.CreateEligibilityRequestMother;
import uk.co.idv.context.usecases.eligibility.EligibilityFactory;
import uk.co.idv.context.usecases.identity.find.FindIdentity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class InternalCreateEligibilityTest {

    private final EligibilityFactory factory = mock(EligibilityFactory.class);
    private final FindIdentity find = mock(FindIdentity.class);

    private final CreateEligibility create = InternalCreateEligibility.builder()
            .factory(factory)
            .find(find)
            .build();

    @Test
    void shouldReturnFoundIdentity() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.build();
        Identity identity = IdentityMother.example();
        given(find.find(request.getAliases())).willReturn(identity);
        Eligibility expectedEligibility = EligibilityMother.build();
        given(factory.build(request, identity)).willReturn(expectedEligibility);

        Eligibility eligibility = create.create(request);

        assertThat(eligibility).isEqualTo(expectedEligibility);
    }

}
