package com.CyberAnssi.YASList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ItemActivity extends AppCompatActivity {

    public LinearLayout layout_items;

    // Display activity_items.xml layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        // Initialize layout_items by finding the LinearLayout in the activity layout
        layout_items = findViewById(R.id.layout_items); // Assuming layout_items is the ID of your LinearLayout in activity_items.xml

        // Getting variables from other activities
        String fileName = getIntent().getStringExtra("fileName");
        String firstLine = getIntent().getStringExtra("firstLine");

        // Replacing the text of the button_addItem button with the firstLine variable
        Button addItemButton = layout_items.findViewById(R.id.button_addItem);
        addItemButton.setText("Add an item for " + firstLine);

// Null check for fileName
        if (fileName != null) {
            // Attempt to open the file only if fileName is not null
            try {
                // Open the file using a BufferedReader
                FileInputStream fis = openFileInput(fileName);
                final BufferedReader[] reader = {new BufferedReader(new InputStreamReader(fis))};
                // Rest of your code for processing the file
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // Handle file not found exception
            }
        } else {
            // Handle the case where fileName is null
            // For CyberAnssi, display an error message or handle it according to your app logic
            Toast.makeText(ItemActivity.this, "Error: fileName is null", Toast.LENGTH_SHORT).show();
        }



        // Find the button_home button in the item view
        ImageButton homeButton = layout_items.findViewById(R.id.button_home);
        // Set a click listener for the button_home
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open another activity
                Intent intent = new Intent(ItemActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Find the button_addItem button in the item view
        //Button addItemButton = layout_items.findViewById(R.id.button_addItem);
        // Set a click listener for the button_addItem
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if fileName is not null before proceeding
                if (fileName != null) {
                    // Open another activity
                    Intent intent = new Intent(ItemActivity.this, PromptItem.class);
                    // passing the fileName variable to PromptItem
                    intent.putExtra("fileName", fileName);
                    // Passing variable firstLine to PromptItem
                    intent.putExtra("firstLine", firstLine);
                    startActivity(intent);
                } else {
                    // Handle the case where fileName is null
                    // For CyberAnssi, display an error message or handle it according to your app logic
                    Toast.makeText(ItemActivity.this, "Error: fileName is null", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // For each line in the file except the first line, add a layout from the line_of_items.xml
        try {
            // Open the file using a BufferedReader
            FileInputStream fis = openFileInput(fileName);
            final BufferedReader[] reader = {new BufferedReader(new InputStreamReader(fis))};

            // Skip the first line
            reader[0].readLine();

            // Create a StringBuilder to hold the content of the file excluding the deleted line
            StringBuilder updatedFileContent = new StringBuilder();

            // Process each line in a for loop
            String line;
            while ((line = reader[0].readLine()) != null) {
                final String currentItem = line; // Declaring line as effectively final

                // Inflate the layout for each line
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.line_of_item, null, false);
                layout_items.addView(layout);

                // Replacing the text of the textView_itemName in the layout with the line
                TextView textView_itemName = layout.findViewById(R.id.textView_itemName);
                textView_itemName.setText(line);

                // Find the delete button in the item view
                ImageButton deleteButton = layout.findViewById(R.id.button_delete_item);

                // Set a click listener for the delete button
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Remove the line from the file
                        try {
                            // Read the existing content of the file and remove the line corresponding to the currentItem
                            FileInputStream fis = openFileInput(fileName);
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
                            StringBuilder updatedContent = new StringBuilder();

                            String line;
                            while ((line = bufferedReader.readLine()) != null) {
                                if (!line.equals(currentItem)) {
                                    updatedContent.append(line).append("\n");
                                }
                            }
                            fis.close();

                            // Write the updated content back to the file
                            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                            fos.write(updatedContent.toString().getBytes());
                            fos.close();

                            // Refresh the activity
                            Intent intent = new Intent(ItemActivity.this, ItemActivity.class);
                            intent.putExtra("fileName", fileName); // Pass the fileName back to the refreshed activity
                            intent.putExtra("firstLine", firstLine); // Pass the firstLine back to the refreshed activity
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Add this flag
                            startActivity(intent);

                            Toast.makeText(ItemActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            // Handle file not found exception
                        } catch (IOException e) {
                            e.printStackTrace();
                            // Handle IO exception
                        }
                    }
                });
            }

            // Close the BufferedReader
            reader[0].close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // Handle file not found exception
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IO exception
        }




    }
}
