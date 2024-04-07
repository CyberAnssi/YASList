package com.CyberAnssi.YASList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public LinearLayout linearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize linearLayout
        linearLayout = findViewById(R.id.linearLayout);

        // Get the application context
        Context context = getApplicationContext();
        // Get the default directory for the application
        File directory = context.getFilesDir();
        // List to store file names
        List<String> fileNames = new ArrayList<>();
        // Get all files in the directory
        File[] files = directory.listFiles();
        // Iterate over files

        if (files != null) {
            for (File file : files) {
                // Check if the file name starts with "vendor_"
                if (file.isFile() && file.getName().startsWith("vendor_")) {
                    fileNames.add(file.getName());
                }
            }
        }

        // Sort the file names
        Collections.sort(fileNames);

        // Print the sorted file names
        for (String fileName : fileNames) {
            // For each file name, read the first line of content and add the layout from the line_of_vendor.xml
            try {
                FileInputStream fis = new FileInputStream(directory + "/" + fileName);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String firstLine = br.readLine();
                br.close();

                // adding a line of vendor
                View vendorView = LayoutInflater.from(this).inflate(R.layout.line_of_vendor, null);

                // Add the item view to the linear layout
                linearLayout.addView(vendorView);

                // Adjust the top margin for the next item
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) vendorView.getLayoutParams();
                layoutParams.topMargin = 16; // Adjust the top margin as needed

                // Replace text of the button with the firstLine
                Button button = vendorView.findViewById(R.id.enter_vendor);
                button.setText(firstLine);

                // If enter_vendor button is clicked, open activity ItemActivity.
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Check if fileName and firstLine are not null before proceeding
                        if (fileName != null && firstLine != null) {
                            // Open another activity
                            Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                            // passing the fileName variable to ItemActivity
                            intent.putExtra("fileName", fileName);
                            // Passing variable firstLine to ItemActivity
                            intent.putExtra("firstLine", firstLine);
                            startActivity(intent);
                        } else {
                            // Handle the case where fileName or firstLine is null
                            // For CyberAnssi, display an error message or handle it according to your app logic
                            Toast.makeText(MainActivity.this, "Error: fileName or firstLine is null", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                // Find the delete button
                ImageButton deleteButton = vendorView.findViewById(R.id.button_deleteVendor);
                // Set a click listener for the button_deleteVendor
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Check if fileName and firstLine are not null before proceeding
                        if (fileName != null && firstLine != null) {
                            // Remove the text file.
                            File file = new File(directory + "/" + fileName);
                            if (file.delete()) {
                                Toast.makeText(MainActivity.this, "File for " + firstLine + " deleted", Toast.LENGTH_SHORT).show();
                                // Refresh the activity
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Flag for only one intent
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "File not deleted", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Handle the case where fileName or firstLine is null
                            // For CyberAnssi, display an error message or handle it according to your app logic
                            Toast.makeText(MainActivity.this, "Error: fileName or firstLine is null", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Find the button_addVendor button in the main view
        Button openVendorNamePromptButton = findViewById(R.id.button_addVendor); // Create vendor button
        openVendorNamePromptButton.setOnClickListener(new View.OnClickListener() { // Cancel button
            @Override
            public void onClick(View v) {
                // Open another activity
                Intent intent = new Intent(MainActivity.this, PromptVendorName.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Flag for only one intent
                startActivity(intent);
            }
        });
    }
}
