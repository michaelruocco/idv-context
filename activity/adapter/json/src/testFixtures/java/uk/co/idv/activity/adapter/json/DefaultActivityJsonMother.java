package uk.co.idv.activity.adapter.json;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface DefaultActivityJsonMother {

    static String build() {
        return loadContentFromClasspath("activity/default-activity.json");
    }

}
