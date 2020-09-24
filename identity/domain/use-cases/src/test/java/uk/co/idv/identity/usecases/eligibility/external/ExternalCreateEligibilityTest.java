package uk.co.idv.identity.usecases.eligibility.external;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.eligibility.IdentityEligibility;
import uk.co.idv.identity.entities.eligibility.IdentityEligibilityMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.usecases.eligibility.CreateEligibility;
import uk.co.idv.identity.entities.eligibility.CreateEligibilityRequest;
import uk.co.idv.identity.entities.eligibility.CreateEligibilityRequestMother;
import uk.co.idv.identity.usecases.eligibility.IdentityEligibilityFactory;
import uk.co.idv.identity.usecases.identity.update.UpdateIdentity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ExternalCreateEligibilityTest {

    private final IdentityEligibilityFactory factory = mock(IdentityEligibilityFactory.class);
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
        IdentityEligibility expectedEligibility = IdentityEligibilityMother.build();
        given(factory.build(request, updatedIdentity)).willReturn(expectedEligibility);

        IdentityEligibility eligibility = create.create(request);

        assertThat(eligibility).isEqualTo(expectedEligibility);
    }

}
