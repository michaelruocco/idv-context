package uk.co.idv.context.adapter.dynamo;

public class WaitForTableToBeActiveException extends RuntimeException {

    public WaitForTableToBeActiveException(Throwable cause) {
        super(cause);
    }

}
