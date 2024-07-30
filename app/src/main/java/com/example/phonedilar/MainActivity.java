package com.example.phonedilar;

//importing
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    //variables
    private static final int REQUEST_CALL_PHONE = 1;
    private EditText editTextNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber = findViewById(R.id.editTextNumber);

        View.OnClickListener dialButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                editTextNumber.append(button.getText().toString());
            }
        };

        // Attach click listener to all dial buttons
        int[] buttonIds = {
                R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6,
                R.id.button7, R.id.button8, R.id.button9,
                R.id.buttonStar, R.id.button0, R.id.buttonHash
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(dialButtonClickListener);
        }

        findViewById(R.id.buttonBackspace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = editTextNumber.getText().toString();
                if (!currentText.isEmpty()) {
                    editTextNumber.setText(currentText.substring(0, currentText.length() - 1));
                }
            }
        });

        findViewById(R.id.buttonCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
    }

    //makes the phone call by requesting permissions and then dialing the number using the intent.action_call function
    private void makePhoneCall() {
        String phoneNumber = editTextNumber.getText().toString();
        if (phoneNumber.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
            } else {
                String dial = "tel:" + phoneNumber;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }
//requests phone call if permission has been granted
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            }
        }
    }
}