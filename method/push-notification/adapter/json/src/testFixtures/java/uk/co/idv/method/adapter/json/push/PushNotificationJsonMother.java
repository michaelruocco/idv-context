package uk.co.idv.method.adapter.json.push;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface PushNotificationJsonMother {

    static String build() {
        return loadContentFromClasspath("push-notification.json");
    }

}
