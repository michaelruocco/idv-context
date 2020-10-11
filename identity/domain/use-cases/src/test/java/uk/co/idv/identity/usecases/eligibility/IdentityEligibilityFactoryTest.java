package uk.co.idv.identity.usecases.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.channel.provideddata.CompositeDataAppender;
import uk.co.idv.identity.entities.channel.provideddata.DataAppender;
import uk.co.idv.identity.entities.channel.provideddata.DataAppenderFactory;
import uk.co.idv.identity.entities.eligibility.CreateEligibilityRequest;
import uk.co.idv.identity.entities.eligibility.CreateEligibilityRequestMother;
import uk.co.idv.identity.entities.eligibility.IdentityEligibility;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class IdentityEligibilityFactoryTest {

    private final DataAppenderFactory appenderFactory = mock(DataAppenderFactory.class);

    private final IdentityEligibilityFactory factory = new IdentityEligibilityFactory(appenderFactory);

    @Test
    void shouldPopulateAliasesFromRequest() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.build();
        Identity identity = IdentityMother.example();
        givenProvidedDataAppender(request.getChannel());

        IdentityEligibility eligibility = factory.build(request, identity);

        assertThat(eligibility.getAliases()).isEqualTo(request.getAliases());
    }

    @Test
    void shouldPopulateChannelFromRequest() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.build();
        Identity identity = IdentityMother.example();
        givenProvidedDataAppender(request.getChannel());

        IdentityEligibility eligibility = factory.build(request, identity);

        assertThat(eligibility.getChannel()).isEqualTo(request.getChannel());
    }

    @Test
    void shouldPopulateRequestedFromRequest() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.build();
        Identity identity = IdentityMother.example();
        givenProvidedDataAppender(request.getChannel());

        IdentityEligibility eligibility = factory.build(request, identity);

        assertThat(eligibility.getRequestedData()).isEqualTo(request.getRequestedData());
    }

    @Test
    void shouldPopulateIdentityWithProvidedDataAppended() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.build();
        Identity identity = IdentityMother.example();
        DataAppender appender = givenProvidedDataAppender(request.getChannel());
        Identity expected = mock(Identity.class);
        given(appender.apply(identity)).willReturn(expected);

        IdentityEligibility eligibility = factory.build(request, identity);

        assertThat(eligibility.getIdentity()).isEqualTo(expected);
    }

    private DataAppender givenProvidedDataAppender(Channel channel) {
        CompositeDataAppender appender = mock(CompositeDataAppender.class);
        given(appenderFactory.build(channel)).willReturn(appender);
        return appender;
    }

}
