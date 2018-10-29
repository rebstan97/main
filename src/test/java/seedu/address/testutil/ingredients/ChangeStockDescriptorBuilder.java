package seedu.address.testutil.ingredients;

import seedu.address.logic.commands.ingredients.StockUpCommand.ChangeStockDescriptor;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.NumUnits;

/**
 * A utility class to help with building ChangeStockDescriptor objects.
 */
public class ChangeStockDescriptorBuilder {

    private ChangeStockDescriptor descriptor;

    public ChangeStockDescriptorBuilder() {
        descriptor = new ChangeStockDescriptor();
    }

    public ChangeStockDescriptorBuilder(ChangeStockDescriptor descriptor) {
        this.descriptor = new ChangeStockDescriptor(descriptor);
    }

    /**
     * Returns an {@code ChangeStockDescriptor} with fields containing {@code ingredientName} and {@code numUnits}.
     */
    public ChangeStockDescriptor buildChangeStockDescriptor(String ingredientName, int numUnits) {
        descriptor = new ChangeStockDescriptor();
        descriptor.setName(new IngredientName(ingredientName));
        descriptor.setNumUnits(new NumUnits(numUnits));
        return descriptor;
    }

    /**
     * Sets the {@code IngredientName} of the {@code EditIngredientDescriptor} that we are building.
     */
    public ChangeStockDescriptorBuilder withName(String name) {
        descriptor.setName(new IngredientName(name));
        return this;
    }

    /**
     * Sets the {@code NumUnits} of the {@code EditIngredientDescriptor} that we are building.
     */
    public ChangeStockDescriptorBuilder withNumUnits(int numUnits) {
        descriptor.setNumUnits(new NumUnits(numUnits));
        return this;
    }

    public ChangeStockDescriptor build() {
        return descriptor;
    }
}
