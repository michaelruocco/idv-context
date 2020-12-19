package uk.co.idv.common.adapter.protector;

import lombok.Builder;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.identity.entities.Updatable;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.method.entities.method.Method;

import java.util.Collection;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Builder
public class DefaultContextDataProtector implements ContextDataProtector {

    private final UnaryOperator<Method> methodProtector;
    private final Collection<DataProtector<?>> dataProtectors;

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
        return channel
                .withEmailAddresses(protect(channel.getEmailAddresses()))
                .withPhoneNumbers(protect(channel.getPhoneNumbers()));
    }

    private Identity protect(Identity identity) {
        return identity
                .withEmailAddresses(protect(identity.getEmailAddresses()))
                .withPhoneNumbers(protect(identity.getPhoneNumbers()));
    }

    private Sequences protect(Sequences sequences) {
        return sequences.updateMethods(methodProtector);
    }

    private EmailAddresses protect(EmailAddresses emailAddresses) {
        return new EmailAddresses(emailAddresses.stream()
                .map(this::protect)
                .collect(Collectors.toList()));
    }

    private PhoneNumbers protect(PhoneNumbers phoneNumbers) {
        return new PhoneNumbers(phoneNumbers.stream()
                .map(this::protect)
                .collect(Collectors.toList()));
    }

    public <T> T protect(Updatable<T> value) {
        UnaryOperator<String> mask = findProtector(value.getClass());
        return value.withValue(mask.apply(value.getValue()));
    }

    private UnaryOperator<String> findProtector(Class<?> type) {
        return dataProtectors.stream()
                .filter(protector -> protector.supports(type))
                .findFirst()
                .orElseThrow(() -> new ProtectionNotSupportedException(type));
    }

}
