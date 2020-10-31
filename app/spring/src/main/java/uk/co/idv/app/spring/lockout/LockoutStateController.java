package uk.co.idv.app.spring.lockout;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.app.manual.Application;
import uk.co.idv.lockout.entities.DefaultExternalLockoutRequest;
import uk.co.idv.lockout.entities.ExternalLockoutRequest;
import uk.co.idv.lockout.entities.policy.LockoutState;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lockout-states")
public class LockoutStateController {

    private final Application application;

    @GetMapping
    public LockoutState getState(@RequestParam String channelId,
                                 @RequestParam String activityName,
                                 @RequestParam String aliasType,
                                 @RequestParam String aliasValue) {
        ExternalLockoutRequest request = DefaultExternalLockoutRequest.builder()
                .channelId(channelId)
                .activityName(activityName)
                .aliases(application.toAliases(aliasType, aliasValue))
                .build();
        return application.loadLockoutState(request);
    }

    @PutMapping
    public LockoutState resetState(@RequestBody ExternalLockoutRequest request) {
        return application.resetLockoutState(request);
    }

}
