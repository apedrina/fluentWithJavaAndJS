package com.alissonpedrina;

import com.alissonpedrina.core.model.ScriptRequest;
import com.alissonpedrina.io.DB;
import com.alissonpedrina.js.ScriptService;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class ScriptServiceTest {

    @BeforeClass
    public static void setUpBefore() throws IOException {
        DB.setDBName(UUID.randomUUID().toString() + ".txt");

    }

    @AfterClass
    public static void downAfter() throws IOException {
        DB.dropDBFile();

    }

    @Test
    public void should_run_script_by_statement() throws IOException {
        double expected = 10;
        String script = "var x = 9;var response = x + 1;";
        ScriptRequest request = new ScriptRequest();
        request.setAction("statement");
        request.setStatement(script);

        double response = (double) ScriptService.run(request);

        Assert.assertTrue(response == expected);

    }

    @Test
    public void should_run_script_by_input() throws IOException {
        double expected = 10;
        String script = "var x = 9;var response = x + 1;";
        File file = Files.createTempFile(UUID.randomUUID().toString(), ".js").toFile();
        file.deleteOnExit();
        Files.write(Paths.get(file.getAbsolutePath()), script.getBytes());
        ScriptRequest request = new ScriptRequest();
        request.setAction("input");
        request.setInput(file.getAbsolutePath());

        double response = (double) ScriptService.run(request);

        Assert.assertTrue(response == expected);

    }

}
