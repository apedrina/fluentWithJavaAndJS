package com.alissonpedrina.core.error;

import java.util.List;
import java.util.stream.Stream;

public class Error {
    public static final String CATEGORY_NAME_EMPTY = "Category name couldn't be empty";
    public static final String CATEGORY_ALREADY_EXIST = "Category already exist.";
    public static final String CATEGORY_NOT_FOUND_FORMAT = "Category: '%s' not found.";
    public static final String CATEGORY_NOT_EXIST_FORMAT = "Category:'%s' doesn't exist.";
    public static final String FRIENDS_YEARS_NONEXISTENT = "Category of type 'friends' need has the years attribute.";
    public static final String FRIENDS_HAS_DESCRIPTION = "Category of type 'friends' don't need has the description field.";
    public static final String FAMILY_WITHOUT_DESCRIPTION = "Category of type 'family' need has the description field.";
    public static final String CONTACT_NAME_EMPTY = "Contact name couldn't be empty";
    public static final String CONTACT_SURNAME_EMPTY = "Contact surName couldn't be empty.";
    public static final String CONTACT_TELEPHONE_EMPTY = "Contact telephone couldn't be empty.";
    public static final String CONTACT_EMAIL_EMPTY = "Contact email couldn't be empty.";
    public static final String CONTACT_EMAIL_INVALID = "Contact email invalid.";
    public static final String CONTACT_ALREADY_EXIST = "Contact already exist.";
    public static final String CONTACT_NOT_FOUND_FORMAT = "Contact with name:'%s' and surname:'%s' not found.";
    public static final String CONTACT_REMOVE_FAIL_FORMAT = "Wasn't possible remove Contact with name:'%s' and surname:'%s'.";
    public static final String ACQUAINTANCE_HAS_YEARS = "Category of type 'acquaintance' don't need has the years field.";
    public static final String ACQUAINTANCE_HAS_DESCRIPTION = "Category of type 'acquaintance' don't need has the description field.";
    private String details;
    private List<String> errors;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

}
