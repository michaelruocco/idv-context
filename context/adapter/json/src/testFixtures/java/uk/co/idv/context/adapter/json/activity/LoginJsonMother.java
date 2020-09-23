package uk.co.idv.context.adapter.json.activity;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface LoginJsonMother {

    static String build() {
        return loadContentFromClasspath("activity/login.json");
    }

}
