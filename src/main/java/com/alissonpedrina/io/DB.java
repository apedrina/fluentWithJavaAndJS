package com.alissonpedrina.io;

import com.alissonpedrina.Main;
import com.alissonpedrina.core.model.AddressBook;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

public class DB {

    private static String DB_NAME = "contacts.txt";
    private static File db;
    private Gson gson;

    public DB() {
        createDBFile();
        gson = new GsonBuilder().setPrettyPrinting().create();

    }

    public static void setDBName(String path) {
        DB_NAME = path;

    }

    public static File getDBFile() {
        return db;

    }

    public static void dropDBFile() {
        if (Objects.nonNull(db) && db.exists()) {
            db.delete();

        }

    }

    private void createDBFile() {
        db = new File(Main.getCliHome() + File.separator + DB_NAME);
        if (!db.exists()) {
            try {
                db.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();

            }

        }

    }

    public synchronized AddressBook read() throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(db.getAbsolutePath()));
        return Optional.ofNullable(gson.fromJson(new String(fileContent), AddressBook.class)).orElse(new AddressBook());

    }

    public synchronized void save(AddressBook addressBook) throws IOException {
        byte[] content = gson.toJson(addressBook).getBytes();
        getDBFile().delete();
        getDBFile().createNewFile();
        WriteFile.write(db, new String(content));

    }

}
