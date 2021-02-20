package uk.co.idv.context.adapter.verification.client.exception;

public class ClientException extends RuntimeException {

    public ClientException(Throwable cause) {
        super(cause);
    }

    public ClientException(String message) {
        super(message);
    }

}
