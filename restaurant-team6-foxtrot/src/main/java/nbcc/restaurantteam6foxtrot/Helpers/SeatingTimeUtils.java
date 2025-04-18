package nbcc.restaurantteam6foxtrot.Helpers;

import java.time.Duration;
import java.time.LocalDateTime;

public class SeatingTimeUtils {

    /**
     * Calculates the available duration between two LocalDateTime values
     * and returns it in hours and minutes.
     *
     * @param startDateTime  The start time
     * @param endDateTime    The end time
     * @return An array containing hours and minutes: [hours, minutes]
     */
    public static int[] calculateDuration(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (startDateTime == null || endDateTime == null || startDateTime.isAfter(endDateTime)) {
            throw new IllegalArgumentException("Invalid date range: start time must be before or equal to end time.");
        }

        Duration duration = Duration.between(startDateTime, endDateTime);
        int hours = (int) duration.toHours();
        int minutes = (int) (duration.toMinutes() % 60);

        return new int[]{hours, minutes};
    }
}

