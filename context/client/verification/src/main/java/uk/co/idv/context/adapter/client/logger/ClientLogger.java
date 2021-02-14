package uk.co.idv.context.adapter.client.logger;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public interface ClientLogger {

    void log(HttpRequest request);

    void log(HttpResponse<String> response);

    void logDuration(long duration);

    void complete();

}
