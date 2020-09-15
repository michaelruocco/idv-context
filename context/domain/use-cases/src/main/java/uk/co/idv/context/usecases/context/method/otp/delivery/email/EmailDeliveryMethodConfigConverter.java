package uk.co.idv.context.usecases.context.method.otp.delivery.email;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.email.EmailDeliveryMethodConfig;
import uk.co.idv.context.usecases.context.method.otp.delivery.DeliveryMethodConfigConverter;
import uk.co.idv.identity.entities.identity.Identity;

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
