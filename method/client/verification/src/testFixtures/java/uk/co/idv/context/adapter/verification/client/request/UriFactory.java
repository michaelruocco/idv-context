package uk.co.idv.context.adapter.verification.client.request;

import java.net.URI;
import java.net.URISyntaxException;

public class UriFactory {

    private UriFactory() {
        // utility class
    }

    public static URI toUri(String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new InvalidUriException(e);
        }
    }

}
