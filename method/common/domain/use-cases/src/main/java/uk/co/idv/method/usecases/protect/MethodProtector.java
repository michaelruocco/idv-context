package uk.co.idv.method.usecases.protect;

import uk.co.idv.method.entities.method.Method;

import java.util.function.UnaryOperator;

public interface MethodProtector extends UnaryOperator<Method> {

    boolean supports(String name);

}
