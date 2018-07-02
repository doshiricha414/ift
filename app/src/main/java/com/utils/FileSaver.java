package com.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class FileSaver {
    public static boolean exportFile(File src, String data) throws IOException {
        boolean isCreated = src.exists() || src.createNewFile();

        if (!isCreated) {
            return false;
        }

        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(src)), true);
        printWriter.write(data);
        printWriter.close();
        /*ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(src));
        oos.writeObject(data);
        oos.close();*/

        return true;
    }

    public static boolean exportFile(File src, Object data) throws IOException {
        boolean isCreated = src.exists() || src.createNewFile();

        if (!isCreated) {
            return false;
        }

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(src));
        oos.writeObject(data);
        oos.close();

        return true;
    }

    public static Object importFile(File src) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(src));
        Object data = ois.readObject();
        ois.close();

        return data;
    }

    public static String importStringFromFile(File src) throws IOException, ClassNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(src));
        String content;
        String data = "";
        while ((content = br.readLine()) != null) {
            data += content;
        }
        br.close();

        return data;
    }
}
