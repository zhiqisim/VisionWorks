package com.zhiqisim.visionworks;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextLicense;
    private EditText editTextPurpose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        editTextName = findViewById(R.id.edit_text_name);
        editTextLicense = findViewById(R.id.edit_text_license);
        editTextPurpose = findViewById(R.id.edit_text_purpose);
        FloatingActionButton buttonAddNote = findViewById(R.id.button_save);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private boolean signIn() {
        String name = editTextName.getText().toString();
        String license = editTextLicense.getText().toString();
        String purpose = editTextPurpose.getText().toString();
        long unixTime = System.currentTimeMillis() / 1000L;

        if (name.trim().isEmpty() || license.trim().isEmpty() || purpose.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a name, license and purpose", Toast.LENGTH_SHORT).show();
            return false;
        }

        CollectionReference notebookRef = FirebaseFirestore.getInstance()
                .collection("LogBook");
        notebookRef.add(new Logs(name, purpose, unixTime, false, license));
        Toast.makeText(this, "Sign-in recorded", Toast.LENGTH_SHORT).show();
        finish();
        return true;
    }
}
