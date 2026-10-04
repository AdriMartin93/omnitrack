package com.omnitrak.catalog_service.domain.models.values;

import lombok.Getter;

import java.time.Duration;
import java.time.Instant;

@Getter
public class TimeRemaining {

    private final long days;
    private final int hours;
    private final int minutes;
    private final boolean isReleased;

    public TimeRemaining(Instant targetDate, Instant now){
        if(targetDate == null || now.isAfter(targetDate)){
            this.days = 0;
            this.hours = 0;
            this.minutes = 0;
            this.isReleased = true;
        } else {
            Duration duration = Duration.between(now, targetDate);
            this.days = duration.toDays();
            this.hours = duration.toHoursPart();
            this.minutes = duration.toMinutesPart();
            this.isReleased = false;
        }
    }

}
