package uk.co.idv.method.entities.otp.policy.delivery.email;

public interface EmailDeliveryMethodConfigMother {

    static EmailDeliveryMethodConfig email() {
        return new EmailDeliveryMethodConfig();
    }

}
