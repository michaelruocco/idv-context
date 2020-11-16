package uk.co.idv.app.manual.adapter.app;

import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.identity.entities.alias.AliasFactory;

import java.time.Clock;

public interface AppAdapter {

    Clock getClock();

    IdGenerator getIdGenerator();

    ErrorHandler getErrorHandler();

    AliasFactory getAliasFactory();

}
