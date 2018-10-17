package seedu.address.model.menu;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * Tests that a {@code Item}'s {@code Name} matches any of the keyword given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Item> {
    private final Set<Tag> keywords;

    public TagContainsKeywordsPredicate(List<String> strList) {
        this.keywords = SampleDataUtil.getTagSet(strList.toArray(new String[strList.size()]));
    }

    @Override
    public boolean test(Item item) {
        return keywords.stream()
                .anyMatch(keyword -> item.getTags().contains(keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                    && keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
