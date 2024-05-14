package core.utils;

import api.models.Person;

import java.io.*;
import java.util.List;

public class DataFileManager {

    public static void exportPeople(List<Person> people, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(people);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Person> importPeople(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Person>) ois.readObject();
        }
    }
}