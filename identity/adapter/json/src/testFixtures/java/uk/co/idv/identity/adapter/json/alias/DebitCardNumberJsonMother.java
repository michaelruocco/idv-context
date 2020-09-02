package uk.co.idv.identity.adapter.json.alias;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface DebitCardNumberJsonMother {

    static String debitCardNumber() {
        return loadContentFromClasspath("alias/debit-card-number.json");
    }

}
