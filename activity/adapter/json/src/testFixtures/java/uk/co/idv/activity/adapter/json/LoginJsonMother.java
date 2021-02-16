package uk.co.idv.activity.adapter.json;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface LoginJsonMother {

    static String build() {
        return loadContentFromClasspath("activity/login.json");
    }

}
