package uk.co.idv.app.spring.info;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class AppNameInfoContributor implements InfoContributor {

    private final String appName;

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("app", Map.of("name", appName));
    }

}
