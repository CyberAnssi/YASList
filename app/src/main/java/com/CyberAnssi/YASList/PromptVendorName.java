package com.CyberAnssi.YASList;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PromptVendorName extends AppCompatActivity {

    // Display prompt for vendor name
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prompt_for_vendor);

        // Cancel button
            Button openMainActivityButton = findViewById(R.id.button_cancel_vendor_creation);
            openMainActivityButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    // Open another activity
                    Intent intent = new Intent(PromptVendorName.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Flag for only one intent
                    startActivity(intent);
                }
            });

        // Create vendor button
            Button createVendorButton = findViewById(R.id.button_create_vendor_name);
            createVendorButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    // Creating text file. The name of the file is current date and time of the system. The file includes the vendor name that was entered by the user in the text field.
                    String vendorName = ((EditText) findViewById(R.id.textfield_vendor_name)).getText().toString();

                    if (vendorName.length() == 0) {
                        Toast.makeText(PromptVendorName.this, "Please enter a vendor name", Toast.LENGTH_SHORT).show();
                    } else {
                    String filename = "vendor_" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".txt";

                    try {


                        FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
                        String vendorNameWithNewline = vendorName + "\n"; // Adding "\n" after vendorName
                        fos.write(vendorNameWithNewline.getBytes());
                        fos.close();


                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                    }

                // If the vendor name was successfully created, the main activity will be opened.
                    if (!vendorName.equals("")) {
                        Intent intent = new Intent(PromptVendorName.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Flag for only one intent
                        startActivity(intent);
                    }






                }
            });
    }
}

