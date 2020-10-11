package uk.co.idv.identity.usecases.eligibility;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.channel.provideddata.DataAppender;
import uk.co.idv.identity.entities.channel.provideddata.DataAppenderFactory;
import uk.co.idv.identity.entities.eligibility.IdentityEligibility;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;

@RequiredArgsConstructor
public class IdentityEligibilityFactory {

    private final DataAppenderFactory factory;

    public IdentityEligibilityFactory() {
        this(new DataAppenderFactory());
    }

    public IdentityEligibility build(FindIdentityRequest request, Identity identity) {
        return IdentityEligibility.builder()
                .aliases(request.getAliases())
                .channel(request.getChannel())
                .requestedData(request.getRequestedData())
                .identity(appendProvidedData(identity, request.getChannel()))
                .build();
    }

    private Identity appendProvidedData(Identity identity, Channel channel) {
        DataAppender appender = factory.build(channel);
        return appender.apply(identity);
    }

}
