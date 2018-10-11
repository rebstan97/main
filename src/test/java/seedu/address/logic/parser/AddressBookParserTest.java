package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.accounts.CreateCommand;
import seedu.address.logic.commands.menu.AddItemCommand;
import seedu.address.logic.commands.menu.ClearMenuCommand;
import seedu.address.logic.commands.menu.DeleteItemCommand;
import seedu.address.logic.commands.menu.ListItemsCommand;
import seedu.address.logic.commands.menu.SelectItemCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.accounts.Account;
import seedu.address.model.menu.Item;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.accounts.AccountBuilder;
import seedu.address.testutil.accounts.AccountUtil;
import seedu.address.testutil.menu.ItemBuilder;
import seedu.address.testutil.menu.ItemUtil;

public class AddressBookParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
        command = (AddCommand) parser.parseCommand(AddCommand.COMMAND_ALIAS
                + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
        command = (DeleteCommand) parser.parseCommand(DeleteCommand.COMMAND_ALIAS
                + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
        command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
        command = (FindCommand) parser.parseCommand(FindCommand.COMMAND_ALIAS + " "
                + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
        command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + " 1") instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS + " 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + " 3") instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS + " 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_remark() throws Exception {
        final Remark remark = new Remark("Some remark.");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + remark.value);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remark), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }

    @Test
    public void parseCommand_createAccount() throws ParseException {
        Account account = new AccountBuilder().build();
        CreateCommand command = (CreateCommand) parser.parseCommand(AccountUtil.getCreateCommand(account));
        assertEquals(new CreateCommand(account), command);
    }

    @Test
    public void parseCommand_createAccount_notEquals() throws ParseException {
        Account accountOneCommand = new AccountBuilder().build();
        Account accountTwoCommand = new AccountBuilder().withUsername("demo1").withPassword("1122qq").build();
        CreateCommand commandOne = (CreateCommand) parser.parseCommand(AccountUtil.getCreateCommand(accountOneCommand));
        CreateCommand commandTwo = (CreateCommand) parser.parseCommand(AccountUtil.getCreateCommand(accountTwoCommand));
        assertNotEquals(commandOne, commandTwo);
    }

    @Test
    public void parseCommand_addItem() throws Exception {
        Item item = new ItemBuilder().build();
        AddItemCommand command = (AddItemCommand) parser.parseCommand(ItemUtil.getAddItemCommand(item));
        assertEquals(new AddItemCommand(item), command);
        command = (AddItemCommand) parser.parseCommand(AddItemCommand.COMMAND_ALIAS
                + " " + ItemUtil.getItemDetails(item));
        assertEquals(new AddItemCommand(item), command);
    }

    @Test
    public void parseCommand_deleteItem() throws Exception {
        DeleteItemCommand command = (DeleteItemCommand) parser.parseCommand(
                DeleteItemCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteItemCommand(INDEX_FIRST_PERSON), command);
        command = (DeleteItemCommand) parser.parseCommand(DeleteItemCommand.COMMAND_ALIAS
                + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteItemCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_listItems() throws Exception {
        assertTrue(parser.parseCommand(ListItemsCommand.COMMAND_WORD) instanceof ListItemsCommand);
        assertTrue(parser.parseCommand(ListItemsCommand.COMMAND_ALIAS) instanceof ListItemsCommand);
        assertTrue(parser.parseCommand(ListItemsCommand.COMMAND_WORD + " 3") instanceof ListItemsCommand);
        assertTrue(parser.parseCommand(ListItemsCommand.COMMAND_ALIAS + " 3") instanceof ListItemsCommand);
    }

    @Test
    public void parseCommand_selectItem() throws Exception {
        SelectItemCommand command = (SelectItemCommand) parser.parseCommand(
                SelectItemCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectItemCommand(INDEX_FIRST_PERSON), command);
        command = (SelectItemCommand) parser.parseCommand(
                SelectItemCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectItemCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_clearMenu() throws Exception {
        assertTrue(parser.parseCommand(ClearMenuCommand.COMMAND_WORD) instanceof ClearMenuCommand);
        assertTrue(parser.parseCommand(ClearMenuCommand.COMMAND_ALIAS) instanceof ClearMenuCommand);
        assertTrue(parser.parseCommand(ClearMenuCommand.COMMAND_WORD + " 3") instanceof ClearMenuCommand);
        assertTrue(parser.parseCommand(ClearMenuCommand.COMMAND_ALIAS + " 3") instanceof ClearMenuCommand);
    }
}
