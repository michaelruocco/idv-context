package uk.co.idv.context.usecases.context;

import uk.co.idv.context.usecases.context.result.NotNextMethodException;

public interface NotNextMethodExceptionMother {

    static NotNextMethodException build() {
        return new NotNextMethodException("default-method");
    }

}
