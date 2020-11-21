package uk.co.idv.app.spring.time;

import lombok.Data;

import java.time.Duration;

@Data
public class UpdateTimeRequest {

    private Duration offset;

}
