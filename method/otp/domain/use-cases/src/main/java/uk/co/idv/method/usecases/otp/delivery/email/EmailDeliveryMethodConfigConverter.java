package uk.co.idv.method.usecases.otp.delivery.email;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfig;
import uk.co.idv.method.entities.otp.policy.delivery.email.EmailDeliveryMethodConfig;
import uk.co.idv.method.usecases.otp.delivery.DeliveryMethodConfigConverter;

@RequiredArgsConstructor
public class EmailDeliveryMethodConfigConverter implements DeliveryMethodConfigConverter {

    private final EmailAddressesConverter emailAddressesConverter;

    public EmailDeliveryMethodConfigConverter() {
        this(new EmailAddressesConverter());
    }

    @Override
    public boolean supports(DeliveryMethodConfig config) {
        return config instanceof EmailDeliveryMethodConfig;
    }

    @Override
    public DeliveryMethods toDeliveryMethods(Identity identity, DeliveryMethodConfig config) {
        EmailDeliveryMethodConfig emailConfig = (EmailDeliveryMethodConfig) config;
        return emailAddressesConverter.toDeliveryMethods(identity.getEmailAddresses(), emailConfig);
    }

}
