package uk.co.idv.context.usecases.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;

import static org.assertj.core.api.Assertions.assertThat;

class EligibilityFactoryTest {

    private final EligibilityFactory factory = new EligibilityFactory();

    @Test
    void shouldPopulateAliasesFromRequest() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.build();
        Identity identity = IdentityMother.example();

        Eligibility eligibility = factory.build(request, identity);

        assertThat(eligibility.getAliases()).isEqualTo(request.getAliases());
    }

    @Test
    void shouldPopulateChannelFromRequest() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.build();
        Identity identity = IdentityMother.example();

        Eligibility eligibility = factory.build(request, identity);

        assertThat(eligibility.getChannel()).isEqualTo(request.getChannel());
    }

    @Test
    void shouldPopulateRequestedFromRequest() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.build();
        Identity identity = IdentityMother.example();

        Eligibility eligibility = factory.build(request, identity);

        assertThat(eligibility.getRequested()).isEqualTo(request.getRequested());
    }

    @Test
    void shouldPopulateIdentity() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.build();
        Identity identity = IdentityMother.example();

        Eligibility eligibility = factory.build(request, identity);

        assertThat(eligibility.getIdentity()).isEqualTo(identity);
    }

}
