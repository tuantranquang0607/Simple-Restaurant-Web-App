package nbcc.restaurantteam6foxtrot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class SeatingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime startDateTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    private int durationMinutes;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    public SeatingTime() {}

    public SeatingTime(LocalDateTime startDateTime, int durationMinutes, Event event) {
        this.startDateTime = startDateTime;
        this.durationMinutes = durationMinutes;
        this.event = event;
    }

    public long getId() { return id; }

    public LocalDateTime getStartDateTime() { return startDateTime; }

    public String getFormattedStartDate() {
        return startDateTime != null
                ? startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : "";
    }

    public String getFormatStartDateTime() {
        return startDateTime != null
                ? startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
                : "";
    }

    public String getFormattedDuration() {
        int hours = getDurationHours();
        int minutes = getDurationRemainingMinutes();

        if (hours == 0) {
            return minutes + " minutes";
        } else if (minutes == 0) {
            return hours + (hours == 1 ? " hour" : " hours");
        } else {
            return hours + (hours == 1 ? " hour " : " hours ") +
                    minutes + (minutes == 1 ? " minute" : " minutes");
        }
    }


    public void setStartDateTime(LocalDateTime startDateTime) { this.startDateTime = startDateTime; }

    public int getDurationMinutes() { return durationMinutes; }

    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public Event getEvent() { return event; }

    public void setEvent(Event event) { this.event = event; }

    public LocalDateTime getEndDateTime() {
        return startDateTime.plusMinutes(durationMinutes);
    }

    public String getFormattedStartDateTime() {
        return event.isSingleDayEvent() ? startDateTime.format(DateTimeFormatter.ofPattern("HH:mm")) : startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

    }

    public String getFormattedEndDateTime() {
        return event.isSingleDayEvent() ? getEndDateTime().format(DateTimeFormatter.ofPattern("HH:mm")) : getEndDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public int getDurationHours() {
        return durationMinutes / 60;
    }

    public int getDurationRemainingMinutes() {
        return durationMinutes % 60;
    }

    public void setDurationFromHoursAndMinutes(int hours, int minutes) {
        this.durationMinutes = (hours * 60) + minutes;
    }

    public void setDurationHours(int hours) {
        this.durationMinutes = (hours * 60) + getDurationRemainingMinutes();
    }

    public void setDurationRemainingMinutes(int minutes) {
        this.durationMinutes = (getDurationHours() * 60) + minutes;
    }
}
