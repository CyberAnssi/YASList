package com.CyberAnssi.YASList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageButton;
import android.widget.LinearLayout;






public class MainActivity_simple_vendor_list extends AppCompatActivity {

    private LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.linearLayout);
        Button addItemButton = findViewById(R.id.button_addVendor);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVendor();
            }
        });
    }

    private void addVendor() {
        // Inflate the "line_of_vendor.xml" layout
        View itemView = LayoutInflater.from(this).inflate(R.layout.line_of_vendor, null);

        // Generate a unique ID for the item view
        int uniqueId = View.generateViewId();
        itemView.setId(uniqueId);

        // Find the delete button in the item view
        ImageButton deleteButton = itemView.findViewById(R.id.button_deleteVendor);

        // Set a click listener for the delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Remove the item view from the linear layout when the delete button is clicked
                ((ViewGroup) itemView.getParent()).removeView(itemView);
            }
        });

        // Add the item view to the linear layout
        mainLayout.addView(itemView);

        // Adjust the top margin for the next item
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) itemView.getLayoutParams();
        layoutParams.topMargin = 16; // Adjust the top margin as needed
    }




}
