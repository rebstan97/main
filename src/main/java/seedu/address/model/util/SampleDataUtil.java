package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.pair.StringPair;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.accounts.Account;
import seedu.address.model.accounts.Password;
import seedu.address.model.accounts.Username;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Account getRootAccount() {
        return new Account(new Username("root"), new Password("1122qq"));
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        sampleAb.addAccount(getRootAccount());
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a map containing the list of stringpair given.
     */
    public static Map<IngredientName, Integer> getRequiredIngredientsMap(StringPair ... stringPairs) {
        Map<IngredientName, Integer> requiredIngredients = new HashMap<>();
        for (StringPair stringPair : stringPairs) {
            IngredientName ingredientName = new IngredientName(stringPair.getFirstString());
            Integer num = Integer.parseInt(stringPair.getSecondString());
            requiredIngredients.put(ingredientName, num);
        }
        return requiredIngredients;
    }
}
