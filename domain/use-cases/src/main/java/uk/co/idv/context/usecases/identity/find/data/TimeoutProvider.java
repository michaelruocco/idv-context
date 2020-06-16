package uk.co.idv.context.usecases.identity.find.data;

import java.time.Duration;

public interface TimeoutProvider {

    Duration getTimeout(String channelId);

}
