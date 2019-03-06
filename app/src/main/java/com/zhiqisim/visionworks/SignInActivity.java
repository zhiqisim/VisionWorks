package com.zhiqisim.visionworks;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextLicense;
    private EditText editTextPurpose;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        final String licensePlate = getIntent().getStringExtra("String");
        editTextName = findViewById(R.id.edit_text_name);
        editTextLicense = findViewById(R.id.edit_text_license);
        editTextPurpose = findViewById(R.id.edit_text_purpose);
        FloatingActionButton buttonAddLog = findViewById(R.id.button_save);
        buttonAddLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        editTextLicense.setText(licensePlate);
    }

    private boolean signIn() {
        String name = editTextName.getText().toString();
        String license = editTextLicense.getText().toString();
        String purpose = editTextPurpose.getText().toString();
        long unixTime = System.currentTimeMillis() / 1000L;
        String strTime = Long.toString(unixTime);

        if (name.trim().isEmpty() || license.trim().isEmpty() || purpose.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a name, license and purpose", Toast.LENGTH_SHORT).show();
            return false;
        }
        String outTime = "NOT EXIT";
        db.collection("LogBook").document(strTime).set(new Logs(name, purpose, unixTime, license, outTime));
        Toast.makeText(this, "Sign-in recorded", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return true;
    }
}
