package uk.co.idv.context.entities.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.context.method.Methods;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class EligibleMethodsContextTest {

    @Test
    void shouldReturnId() {
        UUID id = UUID.randomUUID();

        EligibleMethodsContext context = EligibleMethodsContext.builder()
                .id(id)
                .build();

        assertThat(context.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnActivity() {
        Activity activity = mock(Activity.class);

        EligibleMethodsContext context = EligibleMethodsContext.builder()
                .activity(activity)
                .build();

        assertThat(context.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldReturnMethods() {
        Methods methods = mock(Methods.class);

        EligibleMethodsContext context = EligibleMethodsContext.builder()
                .methods(methods)
                .build();

        assertThat(context.getMethods()).isEqualTo(methods);
    }

    @Test
    void shouldReturnProtectSensitiveData() {
        EligibleMethodsContext context = EligibleMethodsContext.builder()
                .protectSensitiveData(true)
                .build();

        assertThat(context.isProtectSensitiveData()).isTrue();
    }

}
