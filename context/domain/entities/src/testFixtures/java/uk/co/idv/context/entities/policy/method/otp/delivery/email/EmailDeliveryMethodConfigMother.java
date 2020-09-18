package uk.co.idv.context.entities.policy.method.otp.delivery.email;

public interface EmailDeliveryMethodConfigMother {

    static EmailDeliveryMethodConfig email() {
        return new EmailDeliveryMethodConfig();
    }

}
