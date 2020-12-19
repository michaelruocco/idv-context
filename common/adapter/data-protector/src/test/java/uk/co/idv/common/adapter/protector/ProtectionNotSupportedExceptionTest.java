package uk.co.idv.common.adapter.protector;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProtectionNotSupportedExceptionTest {

    @Test
    void shouldReturnTypeClassNameAsMessage() {
        Class<?> type = ProtectionNotSupportedExceptionTest.class;

        Throwable error = new ProtectionNotSupportedException(type);

        assertThat(error.getMessage()).isEqualTo(type.getName());
    }

}
