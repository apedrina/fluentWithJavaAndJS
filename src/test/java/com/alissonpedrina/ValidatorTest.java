package com.alissonpedrina;

import com.alissonpedrina.core.domain.Category;
import com.alissonpedrina.core.domain.Contact;
import com.alissonpedrina.core.error.ValidatorException;
import com.alissonpedrina.core.validator.CategoryValidator;
import com.alissonpedrina.core.validator.ContactValidator;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class ValidatorTest {

    @Test(expected = ValidatorException.class)
    public void should_not_allow_a_category_miss_required_years_field() throws IOException {
        Category category = new Category();
        Contact contact = buildFullContact();
        category.setContacts(Arrays.asList(contact));
        category.setName("friends");

        new CategoryValidator().validate(category);

    }

    @Test(expected = ValidatorException.class)
    public void should_not_allow_a_contact_miss_name() throws IOException {
        Contact contact = buildFullContact();
        contact.setName(null);

        new ContactValidator().validate(contact);

    }

    @Test(expected = ValidatorException.class)
    public void should_not_allow_a_contact_miss_email() throws IOException {
        Contact contact = buildFullContact();
        contact.setEmail(null);

        new ContactValidator().validate(contact);

    }

    @Test(expected = ValidatorException.class)
    public void should_not_allow_a_contact_miss_telephone() throws IOException {
        Contact contact = buildFullContact();
        contact.setTelephone(null);

        new ContactValidator().validate(contact);

    }

    @Test(expected = ValidatorException.class)
    public void should_not_allow_a_contact_miss_surName() throws IOException {
        Contact contact = buildFullContact();
        contact.setSurname(null);

        new ContactValidator().validate(contact);

    }

    private Contact buildFullContact() {
        Contact contact = new Contact();
        contact.setEmail("pedrina.alisson@gmail.com");
        contact.setName("Alisson");
        contact.setTelephone("121212121");
        contact.setSurname("Pedrina");
        contact.setAge(10);
        contact.setHairColor("blue");

        return contact;
    }

}
