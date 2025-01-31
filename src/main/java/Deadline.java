import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy HHmm");

    public Deadline(String description, String by) throws YoChanException {
        super(description);
        try {
            this.by = LocalDateTime.parse(by, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new YoChanException("Ough! Please use the format: YYYY-MM-DD HHMM (e.g., 2024-03-25 1430)");
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}