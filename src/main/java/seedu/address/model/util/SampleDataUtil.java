package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.RestaurantBook;
import seedu.address.model.account.Account;
import seedu.address.model.account.Password;
import seedu.address.model.account.Username;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code RestaurantBook} with sample data.
 */
public class SampleDataUtil {

    private static Account getRootAccount() {
        return new Account(new Username("root"), new Password("1122qq"));
    }

    public static ReadOnlyRestaurantBook getSampleRestaurantBook() {
        RestaurantBook defaultRestaurantBook = new RestaurantBook();
        defaultRestaurantBook.addAccount(getRootAccount());
        return defaultRestaurantBook;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
