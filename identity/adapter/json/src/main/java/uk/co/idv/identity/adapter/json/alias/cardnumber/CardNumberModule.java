package uk.co.idv.identity.adapter.json.alias.cardnumber;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.identity.entities.alias.CardNumber;

public class CardNumberModule extends SimpleModule {

    public CardNumberModule() {
        super("card-number-module", Version.unknownVersion());

        setMixInAnnotation(CardNumber.class, CardNumberMixin.class);

        addDeserializer(CardNumber.class, new CardNumberDeserializer());
    }

}
