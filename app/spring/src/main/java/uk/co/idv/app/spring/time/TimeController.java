package uk.co.idv.app.spring.time;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;

@Profile("test")
@RestController
@RequiredArgsConstructor
@RequestMapping("/time-offset")
@Slf4j
public class TimeController {

    private final OffsetClock clock;

    @PutMapping
    public ResponseEntity<UpdateTimeResponse> update(@RequestBody UpdateTimeRequest request) {
        clock.setOffset(request.getOffset());
        return ResponseEntity.ok(buildResponse());
    }

    @DeleteMapping
    public ResponseEntity<UpdateTimeResponse> reset() {
        clock.reset();
        return ResponseEntity.ok(buildResponse());
    }

    private UpdateTimeResponse buildResponse() {
        Instant now = clock.instant();
        Duration offset = clock.getOffset();
        log.info("time updated to {} using offset {}", now, offset);
        return UpdateTimeResponse.builder()
                .offset(offset)
                .time(now)
                .build();
    }

}
