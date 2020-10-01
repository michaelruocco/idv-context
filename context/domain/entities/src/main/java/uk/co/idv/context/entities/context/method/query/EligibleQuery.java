package uk.co.idv.context.entities.context.method.query;

import uk.co.idv.context.entities.context.method.Method;

import java.util.stream.Stream;

public class EligibleQuery<T extends Method> extends QueryDecorator<T> {

    public EligibleQuery(MethodQuery<T> query) {
        super(query);
    }

    @Override
    public Stream<T> apply(Stream<Method> methods) {
        return super.apply(methods).filter(Method::isEligible);
    }

}
