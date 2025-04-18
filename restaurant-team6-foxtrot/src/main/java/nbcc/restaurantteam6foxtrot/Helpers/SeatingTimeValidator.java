package nbcc.restaurantteam6foxtrot.Helpers;
import nbcc.restaurantteam6foxtrot.entities.Event;
import nbcc.restaurantteam6foxtrot.entities.SeatingTime;
import org.springframework.validation.BindingResult;

import java.time.Duration;
import java.time.LocalDateTime;

public class SeatingTimeValidator {
    /**
     * Validates the seating time.
     * Ensures the start time is within the event's range and the duration does not exceed event end time.
     *
     * @param seatingTime  The seating time object
     * @param event        The related event
     * @param result       BindingResult to store errors
     * @return max duration available in hours and minutes as an int array [hours, minutes]
     */
    public static int[] validateSeatingTime(SeatingTime seatingTime, Event event, BindingResult result) {
        LocalDateTime eventStartDateTime = event.getStartDate().atStartOfDay();
        LocalDateTime eventEndDateTime = event.getEndDate().atTime(23, 59);
        Duration maxDuration = Duration.between(eventStartDateTime, eventEndDateTime);

        int [] eventDuration = {(int) maxDuration.toHours(), 59};

        if (seatingTime.getStartDateTime() == null) {
            result.rejectValue("startDateTime", "error.startDateTime", "Start time is required.");
        } else if (seatingTime.getStartDateTime().isBefore(eventStartDateTime) ||
                seatingTime.getStartDateTime().isAfter(eventEndDateTime)) {
            result.rejectValue("startDateTime", "error.startDateTime", "Start time must be within the event date range.");
        }
        int totalMinutes = seatingTime.getDurationMinutes();


        if (totalMinutes < 1) {

            result.rejectValue("durationMinutes", "error.durationMinutes",  "Duration must be at least 1 minute.");
        } else {

            if (totalMinutes > maxDuration.toMinutes()) {
                result.rejectValue("durationMinutes", "error.durationMinutes",  "Duration exceeds the available event time.");
            }
        }

        return eventDuration;
    }




}
