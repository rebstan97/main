package seedu.address.storage.accounts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.accounts.Account;
import seedu.address.model.accounts.AccountRecord;
import seedu.address.model.accounts.ReadOnlyAccountRecord;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "accountrecord")
public class XmlSerializableAccountRecord {

    public static final String MESSAGE_DUPLICATE_ACCOUNT = "Persons list contains duplicate account(s).";

    @XmlElement
    private List<XmlAdaptedAccount> accounts;

    /**
     * Creates an empty XmlSerializableAddressBook. This empty constructor is required for marshalling.
     */
    public XmlSerializableAccountRecord() {
        accounts = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAccountRecord(ReadOnlyAccountRecord src) {
        this();
        accounts.addAll(src.getAccountList().stream().map(XmlAdaptedAccount::new).collect(Collectors.toList()));
    }

    /**
     * Converts this account record into the model's {@code AccountRecord} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the {@code
     *         XmlAdaptedPerson}.
     */
    public AccountRecord toModelType() throws IllegalValueException {
        AccountRecord addressBook = new AccountRecord();
        for (XmlAdaptedAccount p : accounts) {
            Account account = p.toModelType();
            if (addressBook.hasAccount(account)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ACCOUNT);
            }
            addressBook.addAccount(account);
        }
        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAccountRecord)) {
            return false;
        }
        return accounts.equals(((XmlSerializableAccountRecord) other).accounts);
    }
}
