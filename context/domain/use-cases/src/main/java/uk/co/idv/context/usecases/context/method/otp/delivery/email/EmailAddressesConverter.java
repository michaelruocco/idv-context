package uk.co.idv.context.usecases.context.method.otp.delivery.email;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.policy.method.otp.delivery.email.EmailDeliveryMethodConfig;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EmailAddressesConverter {

    private final EmailAddressConverter addressConverter;

    public EmailAddressesConverter() {
        this(new EmailAddressConverter());
    }

    public DeliveryMethods toDeliveryMethods(EmailAddresses emailAddresses, EmailDeliveryMethodConfig config) {
        return new DeliveryMethods(emailAddresses.stream()
                .map(emailAddress -> addressConverter.toDeliveryMethod(emailAddress, config))
                .collect(Collectors.toList())
        );
    }

}
