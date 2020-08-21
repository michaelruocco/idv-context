package uk.co.idv.app.spring.lockout;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptRequest;
import uk.co.idv.context.usecases.lockout.LockoutFacade;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attempts")
@Slf4j
public class AttemptController {

    private final LockoutFacade facade;

    @PostMapping
    public LockoutState recordAttempt(@RequestBody RecordAttemptRequest request) {
        return facade.recordAttempt(request);
    }

}
