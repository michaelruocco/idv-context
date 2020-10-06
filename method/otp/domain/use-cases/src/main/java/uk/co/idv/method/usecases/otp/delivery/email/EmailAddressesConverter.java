package uk.co.idv.method.usecases.otp.delivery.email;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.policy.delivery.email.EmailDeliveryMethodConfig;

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
