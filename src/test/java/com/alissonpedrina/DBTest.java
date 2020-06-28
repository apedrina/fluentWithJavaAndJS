package com.alissonpedrina;

import com.alissonpedrina.core.domain.Category;
import com.alissonpedrina.core.domain.Contact;
import com.alissonpedrina.core.model.AddressBook;
import com.alissonpedrina.io.DB;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DBTest {
    private static DB db;

    @BeforeClass
    public static void setUpBefore() throws IOException {
        DB.setDBName(UUID.randomUUID().toString() + ".txt");
        db = new DB();

    }

    @AfterClass
    public static void downAfter() throws IOException {
        DB.dropDBFile();

    }

    @Test
    public void should_save_a_contact_with_all_family_required_fields() throws IOException {
        AddressBook addressBook = new AddressBook();
        Category category = new Category();
        Contact contact = buildFullContact();
        category.setContacts(Arrays.asList(contact));
        category.setName("family");
        category.setDescription("my parents");
        List<Category> categories = Arrays.asList(category);

        addressBook.setCategories(categories);
        db.save(addressBook);
        AddressBook addressBookRead = db.read();

        Assert.assertTrue(addressBookRead.getCategories().size() == 1);

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
