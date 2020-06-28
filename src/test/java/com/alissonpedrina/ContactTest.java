package com.alissonpedrina;

import com.alissonpedrina.core.error.ScriptException;
import com.alissonpedrina.core.model.AddressBook;
import com.alissonpedrina.core.model.ScriptRequest;
import com.alissonpedrina.io.DB;
import com.alissonpedrina.js.ScriptService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

public class ContactTest {

    @Before
    public void setUp() throws IOException {
        DB.setDBName(UUID.randomUUID().toString() + ".txt");

    }

    @Test
    public void should_save_contact() throws IOException {
        String insertCategory = "Category.name('myFamily').type('family').description('my parents').save();";
        String expectedName = "alisson";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertCategory));

        String insertContact = "" +
                "Contact.name('alisson')" +
                ".category('family')" +
                ".surname('pedrina')" +
                ".telephone('11111')" +
                ".email('test@gmail.com')" +
                ".age(34)" +
                ".save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertContact));
        String response = String.valueOf(ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, "response = Contact.getByNames('alisson','pedrina').getName();")));

        Assert.assertTrue(response.equalsIgnoreCase(expectedName));

    }

    @Test
    public void should_save_two_contact() throws IOException {
        String insertCategory = "Category.name('myFamily').type('family').description('my parents').save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertCategory));

        String insertContact = "" +
                "Contact.name('alisson')" +
                ".category('family')" +
                ".surname('pedrina')" +
                ".telephone('11111')" +
                ".email('test@gmail.com')" +
                ".age(34)" +
                ".save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertContact));
        insertContact = "" +
                "Contact.name('maria')" +
                ".category('family')" +
                ".surname('pedrina')" +
                ".telephone('11111')" +
                ".email('test@gmail.com')" +
                ".age(34)" +
                ".save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertContact));

        DB db = new DB();
        AddressBook read = db.read();

        Assert.assertTrue(read.getCategories().get(0).getContacts().size() == 2);

    }

    @Test
    public void should_show_all_contacts_order_by_surname() throws IOException {
        String insertCategory = "Category.name('myFamily').type('family').description('my parents').save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertCategory));

        String insertContact = "" +
                "Contact.name('alisson')" +
                ".category('family')" +
                ".surname('c')" +
                ".telephone('11111')" +
                ".email('test@gmail.com')" +
                ".age(34)" +
                ".save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertContact));

        insertContact = "" +
                "Contact.name('maria')" +
                ".category('family')" +
                ".surname('a')" +
                ".telephone('11111')" +
                ".email('test@gmail.com')" +
                ".age(34)" +
                ".save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertContact));

        insertContact = "" +
                "Contact.name('alice')" +
                ".category('family')" +
                ".surname('b')" +
                ".telephone('11111')" +
                ".email('test@gmail.com')" +
                ".age(34)" +
                ".save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertContact));

        insertContact = "" +
                "response = Contact.showAll();";
        String index = (String) ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertContact));
        String[] splitIndex = index.split(";");

        Assert.assertTrue(splitIndex[0].equals("a"));
        Assert.assertTrue(splitIndex[1].equals("b"));
        Assert.assertTrue(splitIndex[2].equals("c"));

    }


    @Test
    public void should_remove_a_contact() throws IOException {
        String insertCategory = "Category.name('myFamily').type('family').description('my parents').save();";
        String expectedName = "maria";

        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertCategory));

        String script = "" +
                "Contact.name('alisson')" +
                ".category('family')" +
                ".surname('pedrina')" +
                ".telephone('11111')" +
                ".email('test@gmail.com')" +
                ".age(34)" +
                ".save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, script));

        script = "" +
                "Contact.name('maria')" +
                ".category('family')" +
                ".surname('pedrina')" +
                ".telephone('11111')" +
                ".email('test@gmail.com')" +
                ".age(34)" +
                ".save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, script));

        script = "Contact.getByNames('alisson','pedrina').remove()";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, script));

        DB db = new DB();
        AddressBook read = db.read();

        Assert.assertTrue(read.getCategories().get(0).getContacts().size() == 1);
        Assert.assertTrue(read.getCategories().get(0).getContacts().get(0).getName().equals(expectedName));

    }

    @Test(expected = ScriptException.class)
    public void should_not_save_contact_with_category_nonexistent() throws IOException {
        String expectedName = "alisson";
        String insertContact = "" +
                "Contact.name('alisson')" +
                ".category('family')" +
                ".surname('pedrina')" +
                ".telephone('11111')" +
                ".email('test@gmail.com')" +
                ".age(34)" +
                ".save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertContact));
        String response = String.valueOf(ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, "response = Contact.getByNames('alisson','pedrina').getName();")));

        Assert.assertTrue(response.equals(expectedName));

    }

    @Test(expected = ScriptException.class)
    public void should_not_save_contact_that_already_exist() throws IOException {
        String insertCategory = "Category.name('myFamily').type('family').description('my parents').save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertCategory));

        String insertContact = "" +
                "Contact.name('alisson')" +
                ".category('family')" +
                ".surname('pedrina')" +
                ".telephone('11111')" +
                ".email('test@gmail.com')" +
                ".age(34)" +
                ".save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertContact));

        insertContact = "" +
                "Contact.name('alisson')" +
                ".category('family')" +
                ".surname('pedrina')" +
                ".telephone('11111')" +
                ".email('test@gmail.com')" +
                ".age(34)" +
                ".save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertContact));

    }

    @Test(expected = ScriptException.class)
    public void should_not_save_contact_without_name() throws IOException {
        String insertContact = "" +
                "Contact" +
                ".category('family')" +
                ".surname('pedrina')" +
                ".telephone('11111')" +
                ".email('test@gmail.com')" +
                ".age(34)" +
                ".save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertContact));

    }

    @Test(expected = ScriptException.class)
    public void should_not_save_contact_without_surname() throws IOException {
        String insertContact = "" +
                "Contact.name('alisson')" +
                ".category('family')" +
                ".telephone('11111')" +
                ".email('test@gmail.com')" +
                ".age(34)" +
                ".save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertContact));

    }

    @Test(expected = ScriptException.class)
    public void should_not_save_contact_without_email() throws IOException {
        String insertContact = "" +
                "Contact.name('alisson')" +
                ".category('family')" +
                ".surname('pedrina')" +
                ".telephone('11111')" +
                ".age(34)" +
                ".save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertContact));

    }

    @Test(expected = ScriptException.class)
    public void should_not_save_contact_without_telephone() throws IOException {
        String insertContact = "" +
                "Contact.name('alisson')" +
                ".category('family')" +
                ".surname('pedrina')" +
                ".email('test@gmail.com')" +
                ".age(34)" +
                ".save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertContact));

    }

    @Test
    public void should_edit_a_contact() throws IOException {
        String insertCategory = "Category.name('myFamily').type('family').description('my parents').save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertCategory));
        String expectedName = "julio";
        String insertContact = "" +
                "Contact.name('alisson')" +
                ".category('family')" +
                ".surname('pedrina')" +
                ".telephone('11111')" +
                ".email('test@gmail.com')" +
                ".age(34)" +
                ".save();";

        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertContact));
        String.valueOf(ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, "Contact.getByNames('alisson','pedrina').setName('julio').setSurname('medina').edit();")));
        String response = String.valueOf(ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, "response = Contact.getByNames('julio','medina').getName();")));

        Assert.assertTrue(response.equals(expectedName));

    }

    @Test(expected = ScriptException.class)
    public void should_not_edit_a_contact_with_invalid_email() throws IOException {
        String insertCategory = "Category.name('myFamily').type('family').description('my parents').save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertCategory));

        String insertContact = "" +
                "Contact.name('alisson')" +
                ".category('family')" +
                ".surname('pedrina')" +
                ".telephone('11111')" +
                ".email('test@gmail.com')" +
                ".age(34)" +
                ".save();";
        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertContact));

        String.valueOf(ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, "Contact.getByNames('alisson','pedrina').setName('julio').setEmail('123').save();")));

    }

    @After
    public void down() {
        DB.dropDBFile();

    }
}
