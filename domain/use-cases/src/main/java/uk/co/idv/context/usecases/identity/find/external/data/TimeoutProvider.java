package uk.co.idv.context.usecases.identity.find.external.data;

import java.time.Duration;

public interface TimeoutProvider {

    Duration getTimeout(String channelId);

}
