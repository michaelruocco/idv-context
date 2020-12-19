package uk.co.idv.method.config;

import uk.co.idv.method.usecases.FakeMethodBuilder;
import uk.co.idv.method.usecases.FakeMethodMasker;
import uk.co.idv.method.usecases.MethodBuilder;
import uk.co.idv.method.usecases.protect.MethodProtector;

public class AppFakeMethodConfig implements AppMethodConfig {

    @Override
    public MethodBuilder methodBuilder() {
        return new FakeMethodBuilder();
    }

    @Override
    public MethodProtector methodProtector() {
        return new FakeMethodMasker();
    }

}
