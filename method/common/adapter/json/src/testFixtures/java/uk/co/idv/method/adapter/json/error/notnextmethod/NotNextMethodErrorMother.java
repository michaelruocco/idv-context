package uk.co.idv.method.adapter.json.error.notnextmethod;

public interface NotNextMethodErrorMother {

    static NotNextMethodError notNextMethodError() {
        return new NotNextMethodError("default-method");
    }

}
