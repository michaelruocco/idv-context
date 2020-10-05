package uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.context.method.otp.delivery.LastUpdatedConfigMixin;
import uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone.simswap.SimSwapConfigModule;
import uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone.sms.SmsDeliveryMethodConfigDeserializer;
import uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone.voice.VoiceDeliveryMethodConfigDeserializer;
import uk.co.idv.method.entities.otp.policy.delivery.LastUpdatedConfig;
import uk.co.idv.method.entities.otp.policy.delivery.phone.OtpPhoneNumberConfig;
import uk.co.idv.method.entities.otp.policy.delivery.phone.PhoneDeliveryMethodConfig;
import uk.co.idv.method.entities.otp.policy.delivery.phone.SmsDeliveryMethodConfig;
import uk.co.idv.method.entities.otp.policy.delivery.phone.VoiceDeliveryMethodConfig;

import java.util.Collections;

public class PhoneDeliveryMethodConfigModule extends SimpleModule {

    public PhoneDeliveryMethodConfigModule() {
        super("phone-delivery-method-config-module", Version.unknownVersion());

        setUpCommon();
        setUpSms();
        setUpVoice();
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new SimSwapConfigModule());
    }

    private void setUpCommon() {
        setMixInAnnotation(PhoneDeliveryMethodConfig.class, PhoneDeliveryMethodConfigMixin.class);
        setMixInAnnotation(OtpPhoneNumberConfig.class, OtpPhoneNumberConfigMixin.class);
        setMixInAnnotation(LastUpdatedConfig.class, LastUpdatedConfigMixin.class);

        addDeserializer(OtpPhoneNumberConfig.class, new OtpPhoneNumberConfigDeserializer());
        addDeserializer(LastUpdatedConfig.class, new LastUpdatedConfigDeserializer());
    }

    private void setUpSms() {
        addDeserializer(SmsDeliveryMethodConfig.class, new SmsDeliveryMethodConfigDeserializer());
    }

    private void setUpVoice() {
        addDeserializer(VoiceDeliveryMethodConfig.class, new VoiceDeliveryMethodConfigDeserializer());
    }

}
