package uk.co.idv.context.adapter.verification.client.header;

public class NoopIdvHeaderValidator implements IdvHeaderValidator {

    @Override
    public void validate(IdvRequestHeaders headers) {
        // intentionally blank
    }

}
