package seedu.address.logic.parser.util;

import java.util.HashMap;
import java.util.Map;

import seedu.address.commons.core.pair.StringPair;

/**
 * Stores mapping of indices to their respective argument pairs.
 * Each key must be associated with exactly two arguments forming a pair.
 * Keys are unique, but two keys may map to duplicate values for argument pairs.
 */
public class ArgumentPairMultimap {

    /** Indices mapped to their respective argument pairs **/
    private final Map<Integer, StringPair> argMultimap = new HashMap<>();

    /**
     * Associates the specified argument value with an integer {@code index} key in this map.
     * Assumes the map does not contain a mapping for the key when a new value
     *
     * @param index   Index key with which the specified argument pair value is to be associated
     * @param argPair Argument value to be associated with the specified index key
     */
    public void put(Integer index, StringPair argPair) {
        argMultimap.put(index, argPair);
    }

    /**
     * Returns the value of {@code index}.
     */
    public StringPair getValue(int index) {
        return argMultimap.get(index);
    }

    /**
     * Returns true if the argumentPairMultimap contains the {@code index} as a key.
     * @param index Index key
     */
    public boolean contains(int index) {
        return argMultimap.containsKey(index);
    }

    /**
     * Returns true if this ArgumentPairMultimap equals {@code other}.
     */
    public boolean equals(Object other) {
        return this == other
                || (other instanceof ArgumentPairMultimap
                    && this.argMultimap.equals(((ArgumentPairMultimap) other).argMultimap));
    }

}
