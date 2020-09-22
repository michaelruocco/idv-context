package uk.co.idv.context.adapter.json.policy.activity;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface OnlinePurchaseJsonMother {

    static String build() {
        return loadContentFromClasspath("activity/online-purchase.json");
    }

}
