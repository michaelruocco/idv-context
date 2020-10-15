package uk.co.idv.method.config;

import uk.co.idv.method.usecases.FakeMethodBuilder;
import uk.co.idv.method.usecases.MethodBuilder;

public class AppFakeMethodConfig implements AppMethodConfig {

    @Override
    public MethodBuilder methodBuilder() {
        return new FakeMethodBuilder();
    }

}
