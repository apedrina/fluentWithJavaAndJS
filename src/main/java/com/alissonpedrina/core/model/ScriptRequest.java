package com.alissonpedrina.core.model;

public class ScriptRequest {

    public ScriptRequest() {

    }


    public ScriptRequest(String action, String statement) {
        this.action = action;
        this.statement = statement;

    }

    private String action;

    private String statement;

    private String input;

    private String output;

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

}
