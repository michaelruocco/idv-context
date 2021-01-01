package uk.co.idv.method.entities.method;

public interface MethodVerifications {

    long countByName(String methodName);

    boolean containsSuccessful(String methodName);

}
