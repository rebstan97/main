package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.salesrecord.ItemName;
import seedu.address.model.salesrecord.Date;
import seedu.address.model.salesrecord.Price;
import seedu.address.model.salesrecord.QuantitySold;
import seedu.address.model.salesrecord.SalesRecord;

/**
 * JAXB-friendly version of the SalesRecord.
 */
public class XmlAdaptedRecord {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Record's %s field is missing!";

    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String itemName;
    @XmlElement(required = true)
    private String quantitySold;
    @XmlElement(required = true)
    private String price;

    /**
     * Constructs an XmlAdaptedRecord.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRecord() {}

    /**
     * Constructs an {@code XmlAdaptedRecord} with the given record details.
     */
    public XmlAdaptedRecord(String date, String itemName, String quantitySold, String price) {
        this.date = date;
        this.itemName = itemName;
        this.quantitySold = quantitySold;
        this.price = price;
    }

    /**
     * Converts a given SalesRecord into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRecord
     */
    public XmlAdaptedRecord(SalesRecord source) {
        date = source.getDate().date;
        itemName = source.getName().fullName;
        quantitySold = String.valueOf(source.getQuantitySold().value);
        price = String.valueOf(source.getPrice().value);
    }

    /**
     * Converts this jaxb-friendly adapted record object into the model's SalesRecord object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted record
     */
    public SalesRecord toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (itemName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, ItemName.class.getSimpleName()));
        }
        if (!ItemName.isValidName(itemName)) {
            throw new IllegalValueException(ItemName.MESSAGE_NAME_CONSTRAINTS);
        }
        final ItemName modelName = new ItemName(itemName);

        if (quantitySold == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    QuantitySold.class.getSimpleName()));
        }
        if (!QuantitySold.isValidQuantity(quantitySold)) {
            throw new IllegalValueException(QuantitySold.MESSAGE_QUANTITY_CONSTRAINTS);
        }
        final QuantitySold modelQuantitySold = new QuantitySold(quantitySold);

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);

        return new SalesRecord(modelDate, modelName, modelQuantitySold, modelPrice);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRecord)) {
            return false;
        }

        XmlAdaptedRecord otherRecord = (XmlAdaptedRecord) other;
        return Objects.equals(date, otherRecord.date)
                && Objects.equals(itemName, otherRecord.itemName)
                && Objects.equals(quantitySold, otherRecord.quantitySold)
                && Objects.equals(price, otherRecord.price);
    }
}
