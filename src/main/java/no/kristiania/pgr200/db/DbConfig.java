package no.kristiania.pgr200.db;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DbConfig {

    public static String URL, USER, PASSWORD;
    public static HashMap<String, String> serverConfig = new HashMap<>();

    static {
        String line;
        String configFile = "db.properties";
        try {
            BufferedReader br = new BufferedReader(new FileReader(configFile));
            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split(":", 2);
                if (parts.length >= 2)
                {
                    String key = parts[0];
                    String value = parts[1];
                    serverConfig.put(key, value);
                } else {
                    System.out.println("ignoring line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error opening db.properties.");
        } catch (IOException e) {
            System.out.println("Error reading db.properties.");
        }

        URL = serverConfig.get("URL").trim();
        USER = serverConfig.get("USER").trim();
        PASSWORD = serverConfig.get("PASSWORD").trim();
    }
}