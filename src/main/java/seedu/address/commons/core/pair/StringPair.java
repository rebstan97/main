package seedu.address.commons.core.pair;

public class StringPair {
    String firstString;
    String secondString;

    public StringPair(String firstString, String secondString) {
        this.firstString = firstString;
        this.secondString = secondString;
    }

    public String getFirstString() {
        return this.firstString;
    }

    public String getSecondString() {
        return this.secondString;
    }
}
