package net.pulsir.lunar.maintenance;

import lombok.Getter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter
public class Maintenance {

    private final String name;
    private final String reason;
    private final int duration;
    private Date endDate;

    public Maintenance(String name, String reason, int duration, Date endDate) {
        this.name = name;
        this.reason = reason;
        this.duration = duration;
        this.endDate = endDate;
    }

    public Maintenance(String name, String reason, int duration) {
        this(name, reason, duration, null);
    }

    public void start() {
        this.endDate = Date.from(Instant.now().plus(this.duration, ChronoUnit.MINUTES));
    }

    public void stop() {
        this.endDate = null;
    }

    public boolean isActive() {
        return this.endDate != null &&
                Date.from(Instant.now()).before(this.endDate);
    }

    public String toString() {
        return "Name: " + this.name
                + "\nReason " + this.reason
                + "\nDuration: " + this.duration
                + "\nEnd date: " + this.endDate;
    }
}