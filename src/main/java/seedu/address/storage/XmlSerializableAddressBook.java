package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.accounts.Account;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.Person;
import seedu.address.storage.elements.XmlAdaptedAccount;
import seedu.address.storage.elements.XmlAdaptedIngredient;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Person list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_ACCOUNT = "Account list contains duplicate account(s).";
    public static final String MESSAGE_DUPLICATE_INGREDIENT = "Ingredient list contains duplicate ingredient(s).";


    @XmlElement
    private List<XmlAdaptedPerson> persons;

    @XmlElement
    private List<XmlAdaptedAccount> accounts;

    @XmlElement
    private List<XmlAdaptedIngredient> ingredients;

    private AddressBook addressBook;

    /**
     * Creates an empty XmlSerializableAddressBook. This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        addressBook = new AddressBook();
        persons = new ArrayList<>();
        accounts = new ArrayList<>();
        ingredients = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        accounts.addAll(src.getAccountList().stream().map(XmlAdaptedAccount::new).collect(Collectors.toList()));
        ingredients.addAll(src.getIngredientList().stream().map(XmlAdaptedIngredient::new)
                .collect(Collectors.toList()));

    }

    /**
     * Converts this person record into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the {@code
     *         XmlAdaptedPerson}.
     */
    public void processPersons() throws IllegalValueException {
        for (XmlAdaptedPerson p : persons) {
            Person person = p.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
    }

    /**
     * Converts this account record into the model's {@code Account} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the {@code
     *         XmlAdaptedAccount}.
     */
    public void processAccounts() throws IllegalValueException {
        for (XmlAdaptedAccount acc : accounts) {
            Account account = acc.toModelType();
            if (addressBook.hasAccount(account)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ACCOUNT);
            }
            addressBook.addAccount(account);
        }
    }

    /**
     * Converts this ingredient record into the model's {@code Ingredient} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the {@code
     *         XmlAdaptedIngredient}.
     */
    public void processIngredients() throws IllegalValueException {
        for (XmlAdaptedIngredient i : ingredients) {
            Ingredient ingredient = i.toModelType();
            if (addressBook.hasIngredient(ingredient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_INGREDIENT);
            }
            addressBook.addIngredient(ingredient);
        }
    }

    /**
     * Returns the converted model {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates record when not
     *         allowed.
     */
    public AddressBook toModelType() throws IllegalValueException {
        processPersons();
        processAccounts();
        processIngredients();
        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAddressBook)) {
            return false;
        }

        return persons.equals(((XmlSerializableAddressBook) other).persons)
                && accounts.equals(((XmlSerializableAddressBook) other).accounts)
                && ingredients.equals(((XmlSerializableAddressBook) other).ingredients);
    }
}
