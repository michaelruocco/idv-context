package uk.co.idv.context.usecases.eligibility.external.data;

import java.time.Duration;

public interface TimeoutProvider {

    Duration getTimeout(String channelId);

}
