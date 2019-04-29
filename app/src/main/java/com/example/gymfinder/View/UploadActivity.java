package com.example.gymfinder.View;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.gymfinder.Model.Gym;
import com.example.gymfinder.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageTask;


public class UploadActivity extends AppCompatActivity{




    private Button uploadBtn;
    private EditText nameEditText;
    private EditText descriptionEditText;
    private ProgressBar uploadProgressBar;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_upload );


        uploadBtn = findViewById(R.id.uploadBtn);
        nameEditText = findViewById(R.id.nameEditText);
        descriptionEditText = findViewById ( R.id.descriptionEditText );

        uploadProgressBar = findViewById(R.id.progress_bar);


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("gyms_uploads");


        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(UploadActivity.this, "An Upload is Still in Progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });
    }


    private void uploadFile() {
            uploadProgressBar.setVisibility(View.VISIBLE);
            uploadProgressBar.setIndeterminate(true);
                {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    uploadProgressBar.setVisibility(View.VISIBLE);
                                    uploadProgressBar.setIndeterminate(false);
                                    uploadProgressBar.setProgress(0);
                                }
                            }, 500);
                            Gym upload = new Gym(nameEditText.getText().toString().trim(),

                                    descriptionEditText.getText ().toString ());

                            String uploadId = mDatabaseRef.push().getKey();

                            mDatabaseRef.child(uploadId).setValue(upload);
                            uploadProgressBar.setVisibility(View.INVISIBLE);
                        }
         {
            Toast.makeText(this, "Gym Review Was Posted Sucesfully", Toast.LENGTH_SHORT).show();
        }

}}