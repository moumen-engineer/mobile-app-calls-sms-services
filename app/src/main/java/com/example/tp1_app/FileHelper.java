package com.example.tp1_app;

import android.content.Context;
import java.io.*;
import java.util.*;

public class FileHelper {

    public static List<String> readFile(Context context, String fileName) {
        List<String> list = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(context.openFileInput(fileName)));

            String line;
            while ((line = br.readLine()) != null) {
                list.add(line.trim());
            }
            br.close();

        } catch (Exception e) {
            // ida kan fichier mayexistich
        }

        return list;
    }
}