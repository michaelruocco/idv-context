package uk.co.idv.identity.usecases.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.eligibility.CreateEligibilityRequest;
import uk.co.idv.identity.entities.eligibility.CreateEligibilityRequestMother;
import uk.co.idv.identity.entities.eligibility.IdentityEligibility;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityEligibilityFactoryTest {

    private final IdentityEligibilityFactory factory = new IdentityEligibilityFactory();

    @Test
    void shouldPopulateAliasesFromRequest() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.build();
        Identity identity = IdentityMother.example();

        IdentityEligibility eligibility = factory.build(request, identity);

        assertThat(eligibility.getAliases()).isEqualTo(request.getAliases());
    }

    @Test
    void shouldPopulateChannelFromRequest() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.build();
        Identity identity = IdentityMother.example();

        IdentityEligibility eligibility = factory.build(request, identity);

        assertThat(eligibility.getChannel()).isEqualTo(request.getChannel());
    }

    @Test
    void shouldPopulateRequestedFromRequest() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.build();
        Identity identity = IdentityMother.example();

        IdentityEligibility eligibility = factory.build(request, identity);

        assertThat(eligibility.getRequestedData()).isEqualTo(request.getRequestedData());
    }

    @Test
    void shouldPopulateIdentity() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.build();
        Identity identity = IdentityMother.example();

        IdentityEligibility eligibility = factory.build(request, identity);

        assertThat(eligibility.getIdentity()).isEqualTo(identity);
    }

}
