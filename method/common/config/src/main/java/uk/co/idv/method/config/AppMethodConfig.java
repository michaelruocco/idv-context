package uk.co.idv.method.config;

import uk.co.idv.method.usecases.MethodBuilder;
import uk.co.idv.method.usecases.protect.MethodProtector;

public interface AppMethodConfig {

    MethodBuilder methodBuilder();

    MethodProtector methodProtector();

}
