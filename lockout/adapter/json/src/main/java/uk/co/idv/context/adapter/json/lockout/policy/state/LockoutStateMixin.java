package uk.co.idv.context.adapter.json.lockout.policy.state;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.context.entities.lockout.attempt.Attempt;
import uk.co.idv.context.entities.lockout.attempt.Attempts;

import java.time.Instant;
import java.util.Collection;

@JsonPropertyOrder({
        "id",
        "idvId",
        "message"
})
public interface LockoutStateMixin {

    @JsonIgnore
    Attempts getAttempts();

    @JsonIgnore
    int getNumberOfAttempts();

    @JsonIgnore
    Instant getMostRecentAttemptTimestamp();

    @JsonProperty("attempts")
    Collection<Attempt> attemptsCollection();

}
