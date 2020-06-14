package uk.co.idv.context.usecases.identity.find.data;

import uk.co.idv.context.entities.alias.Aliases;

import java.time.Duration;

public interface AsyncDataLoadRequest {

    Duration getTimeout();

    Aliases getAliases();

}
