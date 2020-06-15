package uk.co.idv.context.usecases.identity.service.find.data;

import java.time.Duration;

public interface TimeoutProvider {

    Duration getTimeout(String channelId);

}
