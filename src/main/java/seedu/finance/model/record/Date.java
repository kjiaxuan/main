package seedu.finance.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.finance.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Record's date in the finance tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS = "Dates should be of the format dd/mm/yyyy and be a valid date.\n"
            + "Addition of future expenses is not allowed.";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private LocalDate date;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);

        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);

        String[] parsedDate = date.split("/");
        int year = Integer.parseInt(parsedDate[2]);
        int month = Integer.parseInt(parsedDate[1]);
        int day = Integer.parseInt(parsedDate[0]);
        this.setDate(year, month, day);
    }

    /**
     * Constructs a (@code Date).
     *
     * @param date A local date object.
     */
    public Date(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        String[] parsedDate = test.split("/");
        LocalDate date;
        try {
            int year = Integer.parseInt(parsedDate[2]);
            int month = Integer.parseInt(parsedDate[1]);
            int day = Integer.parseInt(parsedDate[0]);
            date = LocalDate.of(year, month, day);
        } catch (Exception e) {
            return false;
        }

        if (date.isAfter(LocalDate.now())) {
            return false;
        }

        return true;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(int year, int month, int day) {
        date = LocalDate.of(year, month, day);
    }

    @Override
    public String toString() {
        return date.format(formatter);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    //Have to check whether this is ok...
    /**
     * Compares two {@code Date} objects based on their calendar dates.
     * Uses {@link LocalDate#compareTo} function to compare dates
     *
     * @param a - First Date to compare
     * @param b - Second Date to compare
     * @return  if b is after a, returns a negative number
     *          if b is before a, returns a positive number
     *          and returns 0 if they are equal
     */
    public static int compare(Date a, Date b) {
        LocalDate first = a.getDate();
        LocalDate second = b.getDate();

        return second.compareTo(first);
    }

    //Not sure if this is correct...
    public boolean isAfter(LocalDate date) {
        return (this.getDate().compareTo(date) > 0);
    }
}
