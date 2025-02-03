package yochan.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import yochan.YoChanException;

/**
 * Represents an event with a start and end time.
 *
 * @author Michael Cheong (Reshiro)
 */
public class Event extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy HHmm");
    private final LocalDateTime from;
    private final LocalDateTime to;


    /**
     * Creates a new Event with the specified description, start time, and end time.
     *
     * @throws YoChanException If the input is invalid.
     */
    public Event(String description, String from, String to) throws YoChanException {
        super(description);
        try {
            this.from = LocalDateTime.parse(from, INPUT_FORMAT);
            this.to = LocalDateTime.parse(to, INPUT_FORMAT);
            if (this.to.isBefore(this.from)) {
                throw new YoChanException("Ough! Event end time cannot be before start time!");
            }
        } catch (DateTimeParseException e) {
            throw new YoChanException("Ough! Please use the format: YYYY-MM-DD HHMM (e.g., 2024-03-25 1430)");
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMAT)
                + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}
