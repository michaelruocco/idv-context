package uk.co.idv.context.entities.context.method.query;

import uk.co.idv.context.entities.context.method.Method;

public interface MethodQueryFactory {

    static <T extends Method> FindMethodOfTypeQuery<T> methodOfType(Class<T> type) {
        return new FindMethodOfTypeQuery<>(type);
    }

    static <T extends Method> IncompleteQuery<T> incomplete(Class<T> type) {
        return new IncompleteQuery<>(methodOfType(type));
    }

    static <T extends Method> MethodQuery<T> incompleteAndEligible(Class<T> type) {
        return new EligibleQuery<>(incomplete(type));
    }

}
