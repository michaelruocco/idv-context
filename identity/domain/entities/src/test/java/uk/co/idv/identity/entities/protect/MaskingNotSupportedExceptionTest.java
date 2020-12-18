package uk.co.idv.identity.entities.protect;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MaskingNotSupportedExceptionTest {

    @Test
    void shouldReturnTypeClassNameAsMessage() {
        Class<?> type = MaskingNotSupportedExceptionTest.class;

        Throwable error = new MaskingNotSupportedException(type);

        assertThat(error.getMessage()).isEqualTo("uk.co.idv.identity.entities.protect.MaskingNotSupportedExceptionTest");
    }

}
