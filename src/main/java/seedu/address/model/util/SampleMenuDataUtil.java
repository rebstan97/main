package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.menu.Item;
import seedu.address.model.menu.Name;
import seedu.address.model.menu.Price;
import seedu.address.model.menu.Recipe;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleMenuDataUtil {
    public static final Recipe EMPTY_RECIPE = new Recipe("");
    public static Item[] getSampleItems() {
        return new Item[] {
            new Item(new Name("Apple Pie"), new Price("3"), EMPTY_RECIPE, getTagSet("pie")),
            new Item(new Name("Beef Stew"), new Price("5"), EMPTY_RECIPE, getTagSet("beef")),
            new Item(new Name("Cheese Fries"), new Price("2"), EMPTY_RECIPE, getTagSet("cheese")),
            new Item(new Name("Donut"), new Price("1.5"), EMPTY_RECIPE, getTagSet("beef")),
            new Item(new Name("Ice Cream"), new Price("1"), EMPTY_RECIPE, getTagSet("dessert")),
            new Item(new Name("Root Beer"), new Price("1"), EMPTY_RECIPE, getTagSet("drink"))
        };
    }

    public static ReadOnlyAddressBook getSampleMenu() {
        AddressBook sampleAb = new AddressBook();
        for (Item sampleItem : getSampleItems()) {
            sampleAb.addItem(sampleItem);
        }
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

}
