package uk.co.idv.method.adapter.push.protect;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.usecases.protect.MethodProtector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

class PushNotificationMaskerTest {

    private final MethodProtector protector = new PushNotificationMasker();

    @Test
    void shouldSupportPushNotification() {
        String name = "push-notification";

        boolean supported = protector.supports(name);

        assertThat(supported).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherMethodName() {
        String name = "other-method";

        boolean supported = protector.supports(name);

        assertThat(supported).isFalse();
    }

    @Test
    void shouldReturnMethodUnchanged() {
        Method method = mock(Method.class);

        Method protectedMethod = protector.apply(method);

        assertThat(protectedMethod).isEqualTo(method);
        verifyNoInteractions(method);
    }

}
