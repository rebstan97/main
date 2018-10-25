package seedu.address.commons.core.pair;

/**
 * Represents a pair of Strings.
 */
public class StringPair {
    private String firstString;
    private String secondString;

    /**
     * Constructs a StringPair object.
     * @param firstString
     * @param secondString
     */
    public StringPair(String firstString, String secondString) {
        this.firstString = firstString;
        this.secondString = secondString;
    }

    /**
     * Returns {@code firstString}.
     */
    public String getFirstString() {
        return this.firstString;
    }

    /**
     * Returns {@code secondString}.
     */
    public String getSecondString() {
        return this.secondString;
    }

    /**
     * Returns true if this StringPair has the same {@code firstString} and {@code secondString} values as {@code
     * other}.
     */
    public boolean equals(Object other) {
        return this == other
                || (other instanceof StringPair
                    && this.firstString.equals(((StringPair) other).firstString)
                    && this.secondString.equals(((StringPair) other).secondString));
    }
}
