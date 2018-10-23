package seedu.address.logic.parser;

import java.util.HashMap;
import java.util.Map;

import seedu.address.commons.core.pair.StringPair;

/**
 * Stores mapping of indices to their respective argument pairs.
 * Each key must be associated with exactly two arguments forming a pair.
 * Values for a given key are stored in a list, and the insertion ordering is maintained.
 * Keys are unique, but the list of argument values may contain duplicate argument values, i.e. the same argument value
 * can be inserted multiple times for the same prefix.
 */
public class ArgumentPairMultimap {

    /** Indices mapped to their respective argument pairs **/
    private final Map<Integer, StringPair> argMultimap = new HashMap<>();

    /**
     * Associates the specified argument value with an integer {@code index} key in this map.
     * Assumes the map does not contain a mapping for the key when a new value
     * If the map previously contained a mapping for the key, the new value is appended to the list of existing values.
     *
     * @param index   Index key with which the specified argument pair value is to be associated
     * @param argPair Argument value to be associated with the specified prefix key
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

}
