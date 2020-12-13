package uk.co.idv.context.adapter.client.request;

import java.net.URI;
import java.net.URISyntaxException;

public class UriFactory {

    public static URI toUri(String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new InvalidUriException(e);
        }
    }

}
