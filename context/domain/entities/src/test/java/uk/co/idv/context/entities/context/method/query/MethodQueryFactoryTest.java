package uk.co.idv.context.entities.context.method.query;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Method;

import static org.assertj.core.api.Assertions.assertThat;

class MethodQueryFactoryTest {

    @Test
    void shouldReturnFindMethodOfTypeWithType() {
        Class<Method> type = Method.class;

        MethodQuery<Method> query = MethodQueryFactory.methodOfType(type);

        assertThat(query).isInstanceOf(FindMethodOfTypeQuery.class);
        assertThat(query.getParent()).isEmpty();
        assertThat(query.getType()).isEqualTo(type);
    }

    @Test
    void shouldReturnIncompleteQuery() {
        Class<Method> type = Method.class;

        MethodQuery<Method> query = MethodQueryFactory.incomplete(type);

        assertThat(query).isInstanceOf(IncompleteQuery.class);
        assertThat(query.getParent()).containsInstanceOf(FindMethodOfTypeQuery.class);
        assertThat(query.getType()).isEqualTo(type);
    }

    @Test
    void shouldReturnEligibleQuery() {
        Class<Method> type = Method.class;

        MethodQuery<Method> query = MethodQueryFactory.incompleteAndEligible(type);

        assertThat(query).isInstanceOf(EligibleQuery.class);
        assertThat(query.getParent()).containsInstanceOf(IncompleteQuery.class);
        assertThat(query.getType()).isEqualTo(type);
    }

}
