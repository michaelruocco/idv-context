package uk.co.idv.context.adapter.verification.client.stub;

import java.util.function.Function;

public interface Scenario<I, O> extends Function<I, O> {

    boolean shouldExecute(I request);

}
