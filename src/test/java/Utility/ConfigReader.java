package Utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = new Properties();

    static {
        try {
            FileInputStream file = new FileInputStream("configuration.properties");
            properties.load(file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String keyword) {
        return properties.getProperty(keyword);
    }

    public static void updateProperty(String keyword, String id) {

        switch (keyword) {
            case "countryID":
                String countryID = id;
                properties.setProperty(keyword, countryID);
                break;
            case "statesID":
                String statesID = id;
                properties.setProperty(keyword, statesID);
                break;
            case "citiesID":
                String citiesID = id;
                properties.setProperty(keyword, citiesID);
                break;
            case "groupID":
                String groupID = id;
                properties.setProperty(keyword, groupID);
                break;
            case "student1":
                String student1 = id;
                properties.setProperty(keyword, student1);
                break;
            case "student2":
                String student2 = id;
                properties.setProperty(keyword, student2);
                break;
            case "student3":
                String student3 = id;
                properties.setProperty(keyword, student3);
                break;
            case "student4":
                String student4 = id;
                properties.setProperty(keyword, student4);
                break;
            case "gradingID":
                String gradingID = id;
                properties.setProperty(keyword, gradingID);
                break;
            case "educationID":
                String educationID = id;
                properties.setProperty(keyword, educationID);
                break;
            case "javaID":
                String javaID = id;
                properties.setProperty(keyword, javaID);
                break;
            case "incidentID":
                String incidentID = id;
                properties.setProperty(keyword, incidentID);
                break;
        }

        FileOutputStream outputFile = null;
        try {
            outputFile = new FileOutputStream("configuration.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            properties.store(outputFile, null);
            outputFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}