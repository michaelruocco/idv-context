package uk.co.idv.context.adapter.json.alias;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.alias.creditcardnumber.CreditCardNumberDeserializer;
import uk.co.idv.context.adapter.json.alias.debitcardnumber.DebitCardNumberDeserializer;
import uk.co.idv.context.adapter.json.alias.defaultalias.DefaultAliasDeserializer;
import uk.co.idv.context.adapter.json.alias.idvid.IdvIdDeserializer;
import uk.co.idv.context.adapter.json.alias.idvid.IdvIdMixin;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.CreditCardNumber;
import uk.co.idv.context.entities.alias.DebitCardNumber;
import uk.co.idv.context.entities.alias.DefaultAlias;
import uk.co.idv.context.entities.alias.IdvId;

public class AliasModule extends SimpleModule {

    public AliasModule() {
        super("aliases-module", Version.unknownVersion());
        setUpDefaults();
        setUpIdvId();
        setUpCardNumbers();
    }

    private void setUpDefaults() {
        setMixInAnnotation(Alias.class, AliasMixin.class);
        setMixInAnnotation(Aliases.class, AliasesMixin.class);
        addDeserializer(Alias.class, new AliasDeserializer());
        addDeserializer(Aliases.class, new AliasesDeserializer());
        addDeserializer(DefaultAlias.class, new DefaultAliasDeserializer());
    }

    private void setUpIdvId() {
        setMixInAnnotation(IdvId.class, IdvIdMixin.class);
        addDeserializer(IdvId.class, new IdvIdDeserializer());
    }

    private void setUpCardNumbers() {
        addDeserializer(CreditCardNumber.class, new CreditCardNumberDeserializer());
        addDeserializer(DebitCardNumber.class, new DebitCardNumberDeserializer());
    }

}
