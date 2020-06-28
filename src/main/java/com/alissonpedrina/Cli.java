package com.alissonpedrina;

import com.alissonpedrina.core.model.ScriptRequest;
import com.alissonpedrina.js.ScriptService;

public class Cli {

    public static final String INPUT_COMMAND = "input";
    public static final String STATEMENT_COMMAND = "statement";

    private static final String MORE_THAN_ONE_COMMAND = "Was found more than one command.";
    private static final String NO_COMMAND = "No valid command found.";
    private static final String STATEMENT_PATTERN = "--statement:";
    private static final String INPUT_PATTERN = "--input:";
    private static final String COLON_PATTERN = ":";
    private static final String MINUS_MINUS_PATTERN = "--";
    private static final String QUOTE_PATTERN = "\"";

    public void execute(ScriptRequest request) {
        try {
            ScriptService.run(request);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public ScriptRequest buildRequest(String[] args) {
        if (args.length > 1) {
            throw new IllegalArgumentException(MORE_THAN_ONE_COMMAND);

        }
        ScriptRequest request = new ScriptRequest();
        try {
            for (String param : args) {
                fillRequest(request, param);

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (request.getAction() == null) {
            throw new IllegalArgumentException(NO_COMMAND);

        }
        return request;

    }

    private void fillRequest(ScriptRequest request, String param) {
        if (param.contains(STATEMENT_PATTERN)) {
            request.setAction(extractParamAction(param));
            request.setStatement(extractParamValue(param));

        } else if (param.contains(INPUT_PATTERN)){
            request.setAction(extractParamAction(param));
            request.setInput(extractParamValue(param));

        }

    }

    private String extractParamAction(String param) {
        int indexBegin = param.indexOf(MINUS_MINUS_PATTERN) + 2;
        int indexEnd = param.indexOf(COLON_PATTERN);
        return param.substring(indexBegin, indexEnd);

    }

    private String extractParamValue(String param) {
        return param.substring(param.indexOf(COLON_PATTERN) + 1, param.length()).replace(QUOTE_PATTERN, "");

    }
}
