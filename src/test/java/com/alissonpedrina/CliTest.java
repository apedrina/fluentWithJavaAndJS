package com.alissonpedrina;

import com.alissonpedrina.core.model.ScriptRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CliTest {

    private Cli cli;

    @Before
    public void setUp() {
        cli = new Cli();

    }

    @Test
    public void should_build_request_with_statement() {
        ScriptRequest request;
        String statement = "var x = 0;";
        String[] args = {String.format("--statement:\"%s\"", statement)};

        request = this.cli.buildRequest(args);

        Assert.assertNotNull(request.getStatement());
        Assert.assertTrue(statement.equals(request.getStatement()));

    }

    @Test
    public void should_build_request_with_input() {
        ScriptRequest request;
        String path = System.getProperty("user.dir");
        String[] args = {String.format("--input:\"%s\"", System.getProperty("user.dir"))};

        request = this.cli.buildRequest(args);

        Assert.assertNotNull(request.getInput());
        Assert.assertTrue(path.equals(request.getInput()));

    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_passed_more_than_one_command() {
        String[] args = {String.format("--input:\"%s\"x", System.getProperty("user.dir")), "--statement:\"x=0;\""};

        this.cli.buildRequest(args);

    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_wrong_command_is_passing() {
        String[] args = {String.format("--xinput:\"%s\"x", System.getProperty("user.dir")),};

        this.cli.buildRequest(args);

    }

}
