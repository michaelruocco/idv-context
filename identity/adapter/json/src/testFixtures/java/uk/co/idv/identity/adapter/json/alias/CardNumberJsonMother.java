package uk.co.idv.identity.adapter.json.alias;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface CardNumberJsonMother {

    static String credit() {
        return loadContentFromClasspath("alias/credit-card-number.json");
    }

    static String debit() {
        return loadContentFromClasspath("alias/debit-card-number.json");
    }

}
