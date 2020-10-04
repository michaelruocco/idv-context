package uk.co.idv.context.usecases.context.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.context.entities.context.NotNextMethodInSequenceException;
import uk.co.idv.context.entities.context.eligibility.EligibilityMother;
import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.context.method.fake.FakeMethodMother;
import uk.co.idv.context.entities.context.result.Result;
import uk.co.idv.context.entities.context.result.ResultMother;
import uk.co.idv.context.entities.context.sequence.SequenceMother;
import uk.co.idv.context.entities.context.sequence.SequencesMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ContextResultUpdaterTest {

    private final ContextResultUpdater updater = new ContextResultUpdater();

    @Test
    void shouldThrowExceptionIfResultMethodIsNotNextInAnySequences() {
        Result result = ResultMother.build();
        Context context = ContextMother.withSequences(SequencesMother.empty());

        Throwable error = catchThrowable(() -> updater.addResultIfApplicable(context, result));

        assertThat(error)
                .isInstanceOf(NotNextMethodInSequenceException.class)
                .hasMessage(result.getMethodName());
    }

    @Test
    void shouldAddResultToAnyMethodsThatMatchResultMethodAndAreNextInSequence() {
        Result result = ResultMother.build();
        Method method1 = FakeMethodMother.withName(result.getMethodName());
        Method method2 = FakeMethodMother.withName(result.getMethodName());
        Context context = ContextMother.withSequences(
                SequenceMother.withMethods(method1),
                SequenceMother.withMethods(method2)
        );

        Context updated = updater.addResultIfApplicable(context, result);

        Context expected = ContextMother.withSequences(
                SequenceMother.withMethods(method1.add(result)),
                SequenceMother.withMethods(method2.add(result))
        );
        assertThat(updated).isEqualTo(expected);
    }

    @Test
    void shouldNotAddResultToIneligibleMethods() {
        Result result = ResultMother.build();
        Method method1 = FakeMethodMother.builder()
                .name(result.getMethodName())
                .eligibility(EligibilityMother.ineligible())
                .build();
        Method method2 = FakeMethodMother.withName(result.getMethodName());
        Context context = ContextMother.withSequences(
                SequenceMother.withMethods(method1),
                SequenceMother.withMethods(method2)
        );

        Context updated = updater.addResultIfApplicable(context, result);

        Context expected = ContextMother.withSequences(
                SequenceMother.withMethods(method1),
                SequenceMother.withMethods(method2.add(result))
        );
        assertThat(updated).isEqualTo(expected);
    }

    @Test
    void shouldNotAddResultToCompleteMethods() {
        Result result = ResultMother.build();
        Method method1 = FakeMethodMother.builder()
                .name(result.getMethodName())
                .complete(true)
                .build();
        Method method2 = FakeMethodMother.withName(result.getMethodName());
        Context context = ContextMother.withSequences(
                SequenceMother.withMethods(method1),
                SequenceMother.withMethods(method2)
        );

        Context updated = updater.addResultIfApplicable(context, result);

        Context expected = ContextMother.withSequences(
                SequenceMother.withMethods(method1),
                SequenceMother.withMethods(method2.add(result))
        );
        assertThat(updated).isEqualTo(expected);
    }

    @Test
    void shouldNotAddResultToCompleteOtherMethods() {
        Result result = ResultMother.build();
        Method method1 = FakeMethodMother.builder()
                .name("other-method-name")
                .build();
        Method method2 = FakeMethodMother.withName(result.getMethodName());
        Context context = ContextMother.withSequences(
                SequenceMother.withMethods(method1),
                SequenceMother.withMethods(method2)
        );

        Context updated = updater.addResultIfApplicable(context, result);

        Context expected = ContextMother.withSequences(
                SequenceMother.withMethods(method1),
                SequenceMother.withMethods(method2.add(result))
        );
        assertThat(updated).isEqualTo(expected);
    }

}
