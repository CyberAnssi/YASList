package com.CyberAnssi.YASList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PromptItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prompt_for_item);

        // Get variables from other activities
        String firstLine = getIntent().getStringExtra("firstLine");
        String fileName = getIntent().getStringExtra("fileName");
        if (fileName == null) {
            // Handle the case where fileName is null
            Toast.makeText(this, "Error: fileName is null", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText itemName = findViewById(R.id.textfield_item_name);
        EditText itemQuantity = findViewById(R.id.textfield_quantity);

        RadioButton radioButtonX = findViewById(R.id.radioButton_x);
        RadioButton radioButtonG = findViewById(R.id.radioButton_g);
        RadioButton radioButtonKg = findViewById(R.id.radioButton_kg);
        RadioButton radioButtoncm = findViewById(R.id.radioButton_cm);
        RadioButton radioButtonm = findViewById(R.id.radioButton_m);
        RadioButton radioButtonml = findViewById(R.id.radioButton_ml);
        RadioButton radioButtonl = findViewById(R.id.radioButton_l);

        Button openMainActivityButton = findViewById(R.id.button_cancel_item_creation);
        openMainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PromptItem.this, ItemActivity.class);
                intent.putExtra("fileName", fileName); // Pass the fileName back to the refreshed activity
                intent.putExtra("firstLine", firstLine); // Pass the firstLine back to the refreshed activity
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Add this flag
                startActivity(intent);
            }
        });

        Button createItemButton = findViewById(R.id.button_create_item_name);
        createItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemNameText = itemName.getText().toString();
                String itemQuantityText = itemQuantity.getText().toString();
                String qType;

                if (radioButtonX.isChecked()) {
                    qType = "x";
                } else if (radioButtonG.isChecked()) {
                    qType = "g";
                } else if (radioButtonKg.isChecked()) {
                    qType = "kg";
                } else if (radioButtoncm.isChecked()) {
                    qType = "cm";
                } else if (radioButtonm.isChecked()) {
                    qType = "m";
                } else if (radioButtonml.isChecked()) {
                    qType = "ml";
                } else if (radioButtonl.isChecked()) {
                    qType = "l";
                } else {
                    qType = "null";
                }

                if (fileName != null && qType != "null" && !itemNameText.isEmpty() && !itemQuantityText.isEmpty()) {
                    try {
                        String fullItem = itemNameText + "  " + itemQuantityText + " " + qType + "\n";
                        FileOutputStream fos = openFileOutput(fileName, Context.MODE_APPEND);
                        fos.write(fullItem.getBytes());
                        fos.close();

                        // getting variable firstLine from ItemActivity
                        String firstLine = getIntent().getStringExtra("firstLine");

                        // Starting ItemActivity
                        Intent intent = new Intent(PromptItem.this, ItemActivity.class);
                        intent.putExtra("fileName", fileName); // Pass the fileName back to the ItemActivity
                        intent.putExtra("firstLine", firstLine); // Pass the firstLine back to the ItemActivity
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Add this flag
                        startActivity(intent);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(PromptItem.this, "Error: File not found", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(PromptItem.this, "Error: IO exception", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PromptItem.this, "Error: Please fill in all fields or cancel", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
