package uk.co.idv.method.adapter.push.protect;

import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.push.PushNotificationName;
import uk.co.idv.method.usecases.protect.MethodProtector;


@RequiredArgsConstructor
public class PushNotificationMasker implements MethodProtector {

    public boolean supports(String name) {
        return PushNotificationName.NAME.equals(name);
    }

    @Override
    public Method apply(Method method) {
        return method;
    }

}
