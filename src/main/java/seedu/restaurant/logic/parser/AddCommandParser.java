package seedu.restaurant.logic.parser;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ADDRESS;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_EMAIL;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_PHONE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_TAG;
import static seedu.restaurant.logic.parser.util.ParserUtil.arePrefixesPresent;

import java.util.Set;

import seedu.restaurant.logic.commands.AddCommand;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ArgumentMultimap;
import seedu.restaurant.logic.parser.util.ArgumentTokenizer;
import seedu.restaurant.logic.parser.util.ParserUtil;
import seedu.restaurant.model.person.Address;
import seedu.restaurant.model.person.Email;
import seedu.restaurant.model.person.Name;
import seedu.restaurant.model.person.Person;
import seedu.restaurant.model.person.Phone;
import seedu.restaurant.model.person.Remark;
import seedu.restaurant.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Remark remark = new Remark("");
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(name, phone, email, address, remark, tagList);

        return new AddCommand(person);
    }
}
