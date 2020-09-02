package uk.co.idv.identity.usecases.eligibility.external.data;

import java.time.Duration;

public interface TimeoutProvider {

    Duration getTimeout(String channelId);

}
