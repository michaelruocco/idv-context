package uk.co.idv.context.usecases.eligibility.external;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.entities.eligibility.EligibilityMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.eligibility.CreateEligibility;
import uk.co.idv.context.entities.eligibility.CreateEligibilityRequest;
import uk.co.idv.context.entities.eligibility.CreateEligibilityRequestMother;
import uk.co.idv.context.usecases.eligibility.EligibilityFactory;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ExternalCreateEligibilityTest {

    private final EligibilityFactory factory = mock(EligibilityFactory.class);
    private final UpdateIdentity update = mock(UpdateIdentity.class);
    private final ExternalFindIdentity find = mock(ExternalFindIdentity.class);

    private final CreateEligibility create = ExternalCreateEligibility.builder()
            .factory(factory)
            .update(update)
            .find(find)
            .build();

    @Test
    void shouldReturnFoundIdentity() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.build();
        Identity foundIdentity = IdentityMother.example();
        given(find.find(request)).willReturn(foundIdentity);
        Identity updatedIdentity = IdentityMother.example1();
        given(update.update(foundIdentity)).willReturn(updatedIdentity);
        Eligibility expectedEligibility = EligibilityMother.build();
        given(factory.build(request, updatedIdentity)).willReturn(expectedEligibility);

        Eligibility eligibility = create.create(request);

        assertThat(eligibility).isEqualTo(expectedEligibility);
    }

}
