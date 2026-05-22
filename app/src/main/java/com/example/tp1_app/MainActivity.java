package com.example.tp1_app;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.io.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    EditText etNumber;
    TextView tvBlack, tvWhite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber = findViewById(R.id.etNumber);
        tvBlack = findViewById(R.id.tvBlacklist);
        tvWhite = findViewById(R.id.tvWhitelist);

        findViewById(R.id.btnAddBlacklist).setOnClickListener(v -> save("blacklist.txt"));
        findViewById(R.id.btnAddWhitelist).setOnClickListener(v -> save("whitelist.txt"));
        findViewById(R.id.btnDelete).setOnClickListener(v -> deleteNumber());

        displayLists();
    }

    private void save(String file) {
        String number = etNumber.getText().toString().trim();
        if (number.isEmpty()) return;

        try {
            FileOutputStream fos = openFileOutput(file, MODE_APPEND);
            fos.write((number + "\n").getBytes());
            fos.close();
        } catch (Exception e) {}

        etNumber.setText("");
        displayLists();
    }

    private void deleteNumber() {
        String number = etNumber.getText().toString().trim();
        if (number.isEmpty()) return;

        removeFromFile("blacklist.txt", number);
        removeFromFile("whitelist.txt", number);


        displayLists();
    }

    private void removeFromFile(String file, String number) {
        List<String> list = FileHelper.readFile(this, file);
        list.removeIf(n -> n.equals(number));

        try {
            FileOutputStream fos = openFileOutput(file, MODE_PRIVATE);
            for (String n : list) {
                fos.write((n + "\n").getBytes());
            }
            fos.close();
        } catch (Exception e) {}
    }

    private void displayLists() {
        List<String> black = FileHelper.readFile(this, "blacklist.txt");
        List<String> white = FileHelper.readFile(this, "whitelist.txt");

        tvBlack.setText("Blacklist:\n" + String.join("\n", black));
        tvWhite.setText("Whitelist:\n" + String.join("\n", white));
    }
}
