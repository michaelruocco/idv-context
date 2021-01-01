
package uk.co.idv.context.entities.context;

public interface NotNextMethodExceptionMother {

    static NotNextMethodException build() {
        return new NotNextMethodException("default-method");
    }

}
