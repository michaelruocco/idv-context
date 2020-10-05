package uk.co.idv.context.adapter.json.context.method;

import uk.co.idv.method.entities.method.Method;

public interface MethodTypeMapping {

    String getName();

    Class<? extends Method> getType();

}
