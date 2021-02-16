package uk.co.idv.activity.entities;

import java.time.Instant;

public interface DefaultActivityMother {

    static DefaultActivity withName(String name) {
        return builder().name(name).build();
    }

    static DefaultActivity withTimestamp(Instant timestamp) {
        return builder().timestamp(timestamp).build();
    }

    static DefaultActivity build() {
        return builder().build();
    }

    static DefaultActivity.DefaultActivityBuilder builder() {
        return DefaultActivity.builder()
                .name("simple-activity")
                .timestamp(Instant.parse("2020-06-06T12:36:15.179Z"));
    }

}
