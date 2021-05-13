package uk.co.idv.method.adapter.json.push.policy;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface PushNotificationPolicyJsonMother {

    static String build() {
        return loadContentFromClasspath("policy/push-notification-policy.json");
    }

}
