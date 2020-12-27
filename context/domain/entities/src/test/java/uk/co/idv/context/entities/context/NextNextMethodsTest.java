package uk.co.idv.context.entities.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.context.method.Methods;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class NextNextMethodsTest {

    @Test
    void shouldReturnId() {
        UUID id = UUID.randomUUID();

        NextMethods context = NextMethods.builder()
                .id(id)
                .build();

        assertThat(context.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnActivity() {
        Activity activity = mock(Activity.class);

        NextMethods context = NextMethods.builder()
                .activity(activity)
                .build();

        assertThat(context.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldReturnMethods() {
        Methods methods = mock(Methods.class);

        NextMethods context = NextMethods.builder()
                .methods(methods)
                .build();

        assertThat(context.getMethods()).isEqualTo(methods);
    }

    @Test
    void shouldReturnProtectSensitiveData() {
        NextMethods context = NextMethods.builder()
                .protectSensitiveData(true)
                .build();

        assertThat(context.isProtectSensitiveData()).isTrue();
    }

}
