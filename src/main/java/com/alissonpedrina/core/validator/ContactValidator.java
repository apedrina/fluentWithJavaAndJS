package com.alissonpedrina.core.validator;

import com.alissonpedrina.core.domain.Contact;
import com.alissonpedrina.core.error.Error;
import com.alissonpedrina.core.error.ValidatorException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactValidator implements Validator<Contact> {

    public static final String ERROR_HEADER = "========CONTACT ERROR=======\n";

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public void validate(Contact contact) {
        List<String> errors = new ArrayList<>();
        if (contact.getName() == null) {
            errors.add(Error.CONTACT_NAME_EMPTY);

        }
        if (contact.getSurname() == null) {
            errors.add(Error.CONTACT_SURNAME_EMPTY);

        }
        if (contact.getTelephone() == null) {
            errors.add(Error.CONTACT_TELEPHONE_EMPTY);

        }
        if (contact.getEmail() == null) {
            errors.add(Error.CONTACT_EMAIL_EMPTY);

        }
        if (contact.getEmail() != null && !emailValidator(contact.getEmail())){
            errors.add(Error.CONTACT_EMAIL_INVALID);

        }

        Error error = new Error();
        error.setDetails(contact.toString());
        error.setErrors(errors);

        if (error.getErrors().size() > 0) {
            StringBuilder errorsMessage = new StringBuilder(ERROR_HEADER + error.getDetails() + "\n");
            for (String categoryError : error.getErrors()) {
                errorsMessage.append(categoryError + "\n");

            }
            throw new ValidatorException(errorsMessage.toString());

        }

    }

    public boolean emailValidator(String email) {
        Matcher matcher = EMAIL_REGEX.matcher(email);
        return matcher.find();

    }

}
