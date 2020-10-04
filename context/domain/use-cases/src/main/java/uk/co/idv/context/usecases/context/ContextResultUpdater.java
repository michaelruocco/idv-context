package uk.co.idv.context.usecases.context;

import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.result.Result;
import uk.co.idv.context.entities.context.sequence.Sequence;
import uk.co.idv.context.entities.context.sequence.Sequences;

import java.util.stream.Collectors;

public class ContextResultUpdater {

    public Context addResultIfApplicable(Context context, Result result) {
        context.getNextEligibleIncompleteMethods(result.getMethodName());
        return context.withSequences(addResultIfApplicable(context.getSequences(), result));
    }

    private Sequences addResultIfApplicable(Sequences sequences, Result result) {
        return new Sequences(sequences.stream()
                .map(sequence -> addResultIfApplicable(sequence, result))
                .collect(Collectors.toList()));
    }

    private Sequence addResultIfApplicable(Sequence sequence, Result result) {
        return sequence.withMethods(addResultIfApplicable(sequence.getMethods(), result));
    }

    private Methods addResultIfApplicable(Methods methods, Result result) {
        return new Methods(methods.stream()
                .map(method -> addResultIfNextAndApplicable(methods, method, result))
                .collect(Collectors.toList()));
    }

    private Method addResultIfNextAndApplicable(Methods methods, Method method, Result result) {
        if (methods.isNext(method.getName())) {
            return addResultIfApplicable(method, result);
        }
        return method;
    }

    private Method addResultIfApplicable(Method method, Result result) {
        if (isApplicable(method, result)) {
            return method.add(result);
        }
        return method;
    }

    private boolean isApplicable(Method method, Result result) {
        return method.hasName(result.getMethodName()) && method.isEligible() && !method.isComplete();
    }

}
