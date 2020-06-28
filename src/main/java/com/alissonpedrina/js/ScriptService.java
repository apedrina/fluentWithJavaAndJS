package com.alissonpedrina.js;

import com.alissonpedrina.Cli;
import com.alissonpedrina.core.domain.Category;
import com.alissonpedrina.core.domain.Contact;
import com.alissonpedrina.core.error.ScriptException;
import com.alissonpedrina.core.model.ScriptRequest;
import com.alissonpedrina.io.DB;
import com.alissonpedrina.io.WriteFile;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import javax.swing.*;

public class ScriptService {

    private static final String RESPONSE_VARIABLE = "response";
    private static final String SOURCE_NAME = "runningScript";

    public static Object run(ScriptRequest request) {
        Context cx = Context.enter();
        cx.getWrapFactory().setJavaPrimitiveWrap(false);
        try {
            Scriptable scope = cx.initStandardObjects();
            putJavaObjects(scope);

            if (Cli.INPUT_COMMAND.equals(request.getAction())) {
                request.setStatement(WriteFile.read(request.getInput()));

            }
            cx.evaluateString(scope, request.getStatement(), SOURCE_NAME, 1, null);
            return scope.get(RESPONSE_VARIABLE, scope);

        } catch (Exception e) {
            throw new ScriptException(e.getMessage());

        } finally {
            Context.exit();

        }

    }

    private static void putJavaObjects(Scriptable scope) {
        Category.CategoryBuilder categoryBuilder = new Category.CategoryBuilder();
        Object categoryWrapped = Context.javaToJS(categoryBuilder, scope);
        ScriptableObject.putProperty(scope, "Category", categoryWrapped);

        Contact.ContactBuilder contactBuilder = new Contact.ContactBuilder();
        Object contactWrapper = Context.javaToJS(contactBuilder, scope);
        ScriptableObject.putProperty(scope, "Contact", contactWrapper);

        DB db = new DB();
        Object dbWrapper = Context.javaToJS(db, scope);
        ScriptableObject.putProperty(scope, "DB", dbWrapper);

    }

}

