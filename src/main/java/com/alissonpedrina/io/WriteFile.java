package com.alissonpedrina.io;

import java.io.*;

public class WriteFile {

    public synchronized static void write(File file, String content) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(content);

        }

    }

    public synchronized static String read(String path) throws IOException {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();

        String line = "";
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");

        }
        return stringBuilder.toString();

    }

}
