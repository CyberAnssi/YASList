import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.CyberAnssi.YASList.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private EditText vendorNameEditText;
    private Button addVendorButton;
    private static final String VENDOR_FILE_PREFIX = "vendor_";
    private static final SimpleDateFormat fileNameDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vendorNameEditText = findViewById(R.id.vendorNameEditText); // Assuming this is the id for your EditText field
        addVendorButton = findViewById(R.id.addVendorButton); // Assuming this is the id for your Add Vendor button

        addVendorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vendorName = vendorNameEditText.getText().toString();
                if (vendorName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a vendor name", Toast.LENGTH_SHORT).show();
                    return;
                }

                saveVendor(vendorName);
                vendorNameEditText.setText(""); // Clear the EditText field
            }
        });
    }

    private void saveVendor(String vendorName) {
        String fileName = VENDOR_FILE_PREFIX + fileNameDateFormat.format(new Date());
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(vendorName.getBytes());
            outputStream.close();
            Toast.makeText(MainActivity.this, "Vendor added successfully!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Error saving vendor!", Toast.LENGTH_SHORT).show();
        }
    }
}
