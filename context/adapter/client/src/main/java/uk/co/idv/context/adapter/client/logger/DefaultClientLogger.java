package uk.co.idv.context.adapter.client.logger;

import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public class DefaultClientLogger implements ClientLogger {

    @Override
    public void log(HttpRequest request) {
        log.info("sending-request:uri:{}:headers:{}",
                request.uri(),
                request.headers().map()
        );
    }

    @Override
    public void log(HttpResponse<String> response) {
        log.info("received-response:status:{}:headers:{}",
                response.statusCode(),
                response.headers().map()
        );
    }

}
