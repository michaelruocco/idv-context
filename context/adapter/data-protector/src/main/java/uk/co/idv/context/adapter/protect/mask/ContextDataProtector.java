package uk.co.idv.context.adapter.protect.mask;

import lombok.Builder;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.method.entities.method.Method;

import java.util.function.UnaryOperator;

@Builder
public class ContextDataProtector implements UnaryOperator<Context> {

    private final UnaryOperator<Method> methodProtector;
    private final UnaryOperator<Identity> identityProtector;
    private final UnaryOperator<Channel> channelProtector;

    @Override
    public Context apply(Context context) {
        return context.toBuilder()
                .request(protect(context.getRequest()))
                .sequences(protect(context.getSequences()))
                .build();
    }

    private ServiceCreateContextRequest protect(ServiceCreateContextRequest request) {
        return request.toBuilder()
                .initial(protect(request.getInitial()))
                .identity(protect(request.getIdentity()))
                .build();
    }

    private CreateContextRequest protect(CreateContextRequest request) {
        return request.withChannel(protect(request.getChannel()));
    }

    private Channel protect(Channel channel) {
        return channelProtector.apply(channel);
    }

    private Identity protect(Identity identity) {
        return identityProtector.apply(identity);
    }

    private Sequences protect(Sequences sequences) {
        return sequences.updateMethods(methodProtector);
    }

}
