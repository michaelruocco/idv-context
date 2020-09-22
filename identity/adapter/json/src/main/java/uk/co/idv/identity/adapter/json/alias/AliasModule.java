package uk.co.idv.identity.adapter.json.alias;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.identity.adapter.json.alias.cardnumber.CardNumberModule;
import uk.co.idv.identity.adapter.json.alias.defaultalias.DefaultAliasDeserializer;
import uk.co.idv.identity.adapter.json.alias.idvid.IdvIdDeserializer;
import uk.co.idv.identity.adapter.json.alias.idvid.IdvIdMixin;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.DefaultAlias;
import uk.co.idv.identity.entities.alias.DefaultAliases;
import uk.co.idv.identity.entities.alias.IdvId;

import java.util.Collections;

public class AliasModule extends SimpleModule {

    public AliasModule() {
        super("alias-module", Version.unknownVersion());
        setUpDefaults();
        setUpIdvId();
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new CardNumberModule());
    }

    private void setUpDefaults() {
        setMixInAnnotation(Alias.class, AliasMixin.class);
        setMixInAnnotation(DefaultAliases.class, AliasesMixin.class);
        addDeserializer(Alias.class, new AliasDeserializer());
        addDeserializer(Aliases.class, new AliasesDeserializer());
        addDeserializer(DefaultAlias.class, new DefaultAliasDeserializer());
    }

    private void setUpIdvId() {
        setMixInAnnotation(IdvId.class, IdvIdMixin.class);
        addDeserializer(IdvId.class, new IdvIdDeserializer());
    }

}
