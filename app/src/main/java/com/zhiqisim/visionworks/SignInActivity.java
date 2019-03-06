package com.zhiqisim.visionworks;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;

import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText editTextName;
    private EditText editTextLicense;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String purpose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        final String licensePlate = getIntent().getStringExtra("String");
        editTextName = findViewById(R.id.edit_text_name);
        editTextLicense = findViewById(R.id.edit_text_license);
        Spinner spinnerPurpose = findViewById(R.id.spinner_purpose);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.purpose, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPurpose.setAdapter(adapter);
        spinnerPurpose.setOnItemSelectedListener(this);
        FloatingActionButton buttonAddLog = findViewById(R.id.button_save);
        buttonAddLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        editTextLicense.setText(licensePlate);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        purpose = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean signIn() {
        String name = editTextName.getText().toString();
        String license = editTextLicense.getText().toString();
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
