package seedu.restaurant.logic.parser.ingredient;

import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NAME_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NUMUNITS_APPLE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NUMUNITS_BROCCOLI;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.logic.commands.ingredient.StockUpCommand;
import seedu.restaurant.logic.commands.ingredient.StockUpCommand.ChangeStockDescriptor;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.ingredient.NumUnits;
import seedu.restaurant.testutil.ingredient.ChangeStockDescriptorBuilder;

public class StockUpCommandParserTest {
    private StockUpCommandParser parser = new StockUpCommandParser();

    @Test
    public void parse_noFields_failure() {
        assertParseFailure(parser, "", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                StockUpCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgumentPair_success() {
        List<ChangeStockDescriptor> descriptorList = new ArrayList<>();
        ChangeStockDescriptor descriptor =
                new ChangeStockDescriptorBuilder().withName(VALID_NAME_APPLE).withNumUnits(VALID_NUMUNITS_APPLE)
                .build();
        descriptorList.add(descriptor);
        StockUpCommand expectedCommand = new StockUpCommand(descriptorList);
        assertParseSuccess(parser, "stockup n/Granny Smith Apple nu/10", expectedCommand);
    }

    @Test
    public void parse_validMultipleArgumentPairs_success() {
        List<ChangeStockDescriptor> descriptorList = new ArrayList<>();
        ChangeStockDescriptor descriptor =
                new ChangeStockDescriptorBuilder().withName(VALID_NAME_APPLE).withNumUnits(VALID_NUMUNITS_APPLE)
                        .build();
        descriptorList.add(descriptor);
        descriptor =
                new ChangeStockDescriptorBuilder().withName(VALID_NAME_BROCCOLI).withNumUnits(VALID_NUMUNITS_BROCCOLI)
                        .build();
        descriptorList.add(descriptor);
        StockUpCommand expectedCommand = new StockUpCommand(descriptorList);
        assertParseSuccess(parser, "stockup n/Granny Smith Apple nu/10 n/Australian Broccoli nu/28",
                expectedCommand);

        IngredientName ingredientName = new IngredientName("Fresh Eggs");
        NumUnits numUnits = new NumUnits(1000);
        descriptor = new ChangeStockDescriptor(ingredientName, numUnits);
        descriptorList.add(descriptor);
        assertParseSuccess(parser,
                "stockup n/Granny Smith Apple nu/10 n/Australian Broccoli nu/28 n/Fresh Eggs nu/1000",
                expectedCommand);
    }
}
