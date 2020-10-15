package uk.co.idv.app.spring.lockout;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.lockout.entities.DefaultExternalLockoutRequest;
import uk.co.idv.lockout.entities.ExternalLockoutRequest;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.usecases.LockoutFacade;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lockout-states")
public class LockoutStateController {

    private final LockoutFacade facade;

    @GetMapping
    public LockoutState getState(@RequestParam String channelId,
                                 @RequestParam String activityName,
                                 @RequestParam String aliasType,
                                 @RequestParam String aliasValue) {
        ExternalLockoutRequest request = DefaultExternalLockoutRequest.builder()
                .channelId(channelId)
                .activityName(activityName)
                .aliases(facade.toAliases(aliasType, aliasValue))
                .build();
        return facade.loadState(request);
    }

    @PutMapping
    public LockoutState resetState(@RequestBody ExternalLockoutRequest request) {
        return facade.resetState(request);
    }

}
