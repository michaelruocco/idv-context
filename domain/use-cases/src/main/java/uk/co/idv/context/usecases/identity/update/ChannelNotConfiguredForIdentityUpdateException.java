package uk.co.idv.context.usecases.identity.update;

public class ChannelNotConfiguredForIdentityUpdateException extends RuntimeException {

    public ChannelNotConfiguredForIdentityUpdateException(String message) {
        super(message);
    }

}
