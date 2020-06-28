package com.alissonpedrina;

import com.alissonpedrina.core.error.ScriptException;
import com.alissonpedrina.core.model.ScriptRequest;
import com.alissonpedrina.io.DB;
import com.alissonpedrina.js.ScriptService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

public class CategoryTest {

    @Before
    public void setUp() throws IOException {
        DB.setDBName(UUID.randomUUID().toString() + ".txt");

    }

    @Test
    public void should_save_category() throws IOException {
        String insertCategory = "Category.name('parents').type('family').description('my parents').save();";
        String expectedName = "parents";

        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertCategory));
        String response = String.valueOf(ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, "response = Category.getByName('parents').getName();")));

        Assert.assertTrue(response.equalsIgnoreCase(expectedName));

    }

    @Test(expected = ScriptException.class)
    public void should_delete_category() throws IOException {
        String insertCategory = "Category.name('parents').type('family').description('my parents').save();";
        String expectedName = "parents";

        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insertCategory));
        String response = String.valueOf(ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, "response = Category.getByName('parents').getName();")));

        Assert.assertTrue(response.equalsIgnoreCase(expectedName));

        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, "Category.getByName('parents').remove();"));
        String.valueOf(ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, "response = Category.getByName('parents').getName();")));

    }

    @Test(expected = ScriptException.class)
    public void should_not_save_a_category_that_already_exist() throws IOException {
        String insert = "Category.name('parents').type('family').description('my parents').save();";

        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insert));

        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, insert));

    }

    @Test(expected = ScriptException.class)
    public void should_not_save_a_friends_category_without_years_field() throws IOException {
        String script = "Category.name('jobFriends').type('friends').save();";

        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, script));

    }

    @Test(expected = ScriptException.class)
    public void should_not_save_a_category_type_that_is_not_allowed() throws IOException {
        String script = "Category.name('jobFriends').type('jobs').save();";

        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, script));

    }

    @Test(expected = ScriptException.class)
    public void should_not_save_a_friends_category_with_description() throws IOException {
        String script = "Category.name('jobFriends').type('friends').description('my friends').save();";

        ScriptService.run(new ScriptRequest(Cli.STATEMENT_COMMAND, script));

    }

    @After
    public void down() {
        DB.dropDBFile();

    }
}
