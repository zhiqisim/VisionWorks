package com.zhiqisim.visionworks;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraPreviewActivity extends AppCompatActivity {
    private EditText editTextLicense;
    private Button retakeBtn;
    private Button nextBtn;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "CameraPreviewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);
        retakeBtn = findViewById(R.id.button_retake);
        nextBtn = findViewById(R.id.button_next);
        editTextLicense = findViewById(R.id.license_verification);
        String licensePlate = getIntent().getStringExtra("String");
        licensePlate = licensePlate.replaceAll("\\s","");
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
                next();
            }
        });
    }

    private void next() {
        CollectionReference colRef = db.collection("LogBook");
        Query query = colRef.whereEqualTo("license", editTextLicense.getText().toString()).whereEqualTo("outTime", "NOT EXIT");;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    boolean check = true;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        long unixTime = System.currentTimeMillis();
                        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, HH:mm");
                        Date date = new java.util.Date(unixTime);
                        String formattedDate = sdf.format(date);
                        if(document.exists()) {
                            check = false;
                            DocumentReference docRef = db.collection("LogBook").document(document.getId());
                            docRef
                                    .update("outTime", formattedDate)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully updated!");
                                            Intent intent = new Intent(CameraPreviewActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error updating document", e);

                                        }
                                    });
                        }
                    }
                    if(check){
                        Intent intent = new Intent(CameraPreviewActivity.this, SignInActivity.class);
                        intent.putExtra("String", editTextLicense.getText().toString());
                        startActivity(intent);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

}
