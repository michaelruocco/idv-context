package uk.co.idv.method.config.push;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.method.adapter.push.protect.PushNotificationMasker;
import uk.co.idv.method.config.AppMethodConfig;
import uk.co.idv.method.usecases.MethodBuilder;
import uk.co.idv.method.usecases.protect.MethodProtector;
import uk.co.idv.method.usecases.push.PushNotificationBuilder;

@Slf4j
public class AppPushNotificationConfig implements AppMethodConfig {

    @Override
    public MethodBuilder methodBuilder() {
        return new PushNotificationBuilder();
    }

    @Override
    public MethodProtector methodProtector() {
        return new PushNotificationMasker();
    }

}
