package uk.co.idv.context.adapter.json.alias;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface CreditCardNumberJsonMother {

    static String creditCardNumber() {
        return loadContentFromClasspath("alias/credit-card-number.json");
    }

}
