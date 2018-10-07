package seedu.address.storage.elements;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientPrice;
import seedu.address.model.ingredient.IngredientUnit;
import seedu.address.model.ingredient.MinimumUnit;
import seedu.address.model.ingredient.NumUnits;

/**
 * JAXB-friendly version of the Ingredient.
 */
public class XmlAdaptedIngredient {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Ingredient's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String unit;
    @XmlElement(required = true)
    private String price;
    @XmlElement(required = true)
    private String minimum;
    @XmlElement(required = true)
    private String numUnits;

    /**
     * Constructs an XmlAdaptedIngredient.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedIngredient() {}

    /**
     * Constructs an {@code XmlAdaptedIngredient} with the given ingredient details.
     */
    public XmlAdaptedIngredient(String name, String unit, String price, String minimum, String numUnits) {
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.minimum = minimum;
        this.numUnits = numUnits;
    }

    /**
     * Converts a given Ingredient into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedIngredient
     */
    public XmlAdaptedIngredient(Ingredient source) {
        name = source.getName().fullName;
        unit = source.getUnit().unitName;
        price = source.getPrice().pricePerUnit;
        minimum = source.getMinimum().minimumUnit;
        numUnits = source.getNumUnits().numberOfUnits;
    }

    /**
     * Converts this jaxb-friendly adapted ingredient object into the model's Ingredient object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ingredient
     */
    public Ingredient toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    IngredientName.class.getSimpleName()));
        }
        if (!IngredientName.isValidName(name)) {
            throw new IllegalValueException(IngredientName.MESSAGE_NAME_CONSTRAINTS);
        }
        final IngredientName modelName = new IngredientName(name);

        if (unit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    IngredientUnit.class.getSimpleName()));
        }
        if (!IngredientUnit.isValidUnit(unit)) {
            throw new IllegalValueException(IngredientUnit.MESSAGE_UNIT_CONSTRAINTS);
        }
        final IngredientUnit modelUnit = new IngredientUnit(unit);

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    IngredientPrice.class.getSimpleName()));
        }
        if (!IngredientPrice.isValidPrice(price)) {
            throw new IllegalValueException(IngredientPrice.MESSAGE_PRICE_CONSTRAINTS);
        }
        final IngredientPrice modelPrice = new IngredientPrice(price);

        if (minimum == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MinimumUnit.class.getSimpleName()));
        }
        if (!MinimumUnit.isValidMinimum(minimum)) {
            throw new IllegalValueException(MinimumUnit.MESSAGE_MINIMUM_CONSTRAINTS);
        }
        final MinimumUnit modelMinimum = new MinimumUnit(minimum);

        if (numUnits == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    NumUnits.class.getSimpleName()));
        }
        if (!NumUnits.isValidNumUnits(numUnits)) {
            throw new IllegalValueException(NumUnits.MESSAGE_NUMUNITS_CONSTRAINTS);
        }
        final NumUnits modelNumUnits = new NumUnits(numUnits);

        return new Ingredient(modelName, modelUnit, modelPrice, modelMinimum, modelNumUnits);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedIngredient)) {
            return false;
        }

        XmlAdaptedIngredient otherIngredient = (XmlAdaptedIngredient) other;
        return Objects.equals(name, otherIngredient.name)
                && Objects.equals(unit, otherIngredient.unit)
                && Objects.equals(price, otherIngredient.price)
                && Objects.equals(minimum, otherIngredient.minimum)
                && Objects.equals(numUnits, otherIngredient.numUnits);
    }
}
