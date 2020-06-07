package uk.co.idv.context.entities.alias;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AliasTest {

    @Test
    void isTypeShouldReturnTrueIfTypeMatches() {
        Alias alias = IdvIdMother.build();

        assertThat(alias.isType("idv-id")).isTrue();
    }

    @Test
    void isTypeShouldReturnFalseIfTypeDoesNotMatch() {
        Alias alias = IdvIdMother.build();

        assertThat(alias.isType("other-type")).isFalse();
    }

}
