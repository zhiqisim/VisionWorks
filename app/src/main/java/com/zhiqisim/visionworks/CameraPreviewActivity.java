package com.zhiqisim.visionworks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.Button;

public class CameraPreviewActivity extends AppCompatActivity {
    private EditText editTextLicense;
    private Button retakeBtn;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);
        retakeBtn = findViewById(R.id.button_retake);
        nextBtn = findViewById(R.id.button_next);
        editTextLicense = findViewById(R.id.license_verification);
        final String licensePlate = getIntent().getStringExtra("String");
        editTextLicense.setText(licensePlate);
        retakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CameraPreviewActivity.this, SignInActivity.class);
                intent.putExtra("String", editTextLicense.getText().toString());
                startActivity(intent);
            }
        });
    }
}
