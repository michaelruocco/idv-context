package uk.co.idv.app.manual.adapter.app;

import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.mruoc.randomvalue.uuid.UuidGenerator;

import java.time.Clock;

public interface AppAdapter {

    Clock getClock();

    UuidGenerator getUuidGenerator();

    ErrorHandler getErrorHandler();

    AliasFactory getAliasFactory();

}
