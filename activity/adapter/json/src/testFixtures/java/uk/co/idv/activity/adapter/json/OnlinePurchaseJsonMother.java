package uk.co.idv.activity.adapter.json;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface OnlinePurchaseJsonMother {

    static String build() {
        return loadContentFromClasspath("activity/online-purchase.json");
    }

}
