package uk.co.idv.context.adapter.client.request;

import uk.co.idv.context.adapter.client.headers.ContextRequestHeadersMother;

import java.util.UUID;

public interface ClientGetContextRequestMother {

    static ClientGetContextRequest build() {
        return ClientGetContextRequest.builder()
                .id(UUID.fromString("fb231884-0c4e-4691-847c-f3105df4a201"))
                .headers(ContextRequestHeadersMother.build())
                .build();
    }

}
