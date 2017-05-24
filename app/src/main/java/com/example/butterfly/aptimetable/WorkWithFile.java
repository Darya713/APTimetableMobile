package com.example.butterfly.aptimetable;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {
    private static String fileName = "markTimetable.json";

    public static void saveDate(Context context, String json) {
        try {
            FileWriter fileWriter = new FileWriter(context.getFilesDir().getPath() + "/" + fileName);
            fileWriter.write(json);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static String getDate(Context context) {
        try {
            File file = new File(context.getFilesDir().getPath() + "/" + fileName);
            FileInputStream inputStream = new FileInputStream(file);
            int size = inputStream.available();
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            inputStream.close();
            return new String(bytes);
        } catch (IOException e) {
            e.getMessage();
            return null;
        }
    }
}
