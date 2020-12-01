package uk.co.idv.context.adapter.json.context.error.notnextmethod;


import uk.co.idv.context.adapter.json.error.notnextmethod.NotNextMethodError;

public interface NotNextMethodErrorMother {

    static NotNextMethodError notNextMethodError() {
        return new NotNextMethodError("default-method");
    }

}
