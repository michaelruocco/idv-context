package uk.co.idv.context.entities.context.method.query;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.otp.Otp;
import uk.co.idv.context.entities.context.method.otp.OtpMother;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.idv.context.entities.context.method.MockMethodMother.mockMethod;

class FindMethodOfTypeQueryTest {

    @Test
    void shouldReturnEmptyParentQuery() {
        FindMethodOfTypeQuery<Otp> find = new FindMethodOfTypeQuery<>(Otp.class);

        assertThat(find.getParent()).isEmpty();
    }

    @Test
    void shouldReturnEmptyOptionalIfNoMethodsOfTypeInStream() {
        FindMethodOfTypeQuery<Otp> find = new FindMethodOfTypeQuery<>(Otp.class);

        Stream<Otp> otp = find.apply(Stream.of(mockMethod(), mockMethod()));

        assertThat(otp).isEmpty();
    }

    @Test
    void shouldReturnMethodOfTypePresentInStream() {
        FindMethodOfTypeQuery<Otp> find = new FindMethodOfTypeQuery<>(Otp.class);
        Otp expected = OtpMother.build();

        Stream<Otp> otp = find.apply(Stream.of(mockMethod(), expected));

        assertThat(otp).contains(expected);
    }

}
