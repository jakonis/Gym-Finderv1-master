package com.example.gymfinder.View;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gymfinder.Adapter.RecyclerAdapter;
import com.example.gymfinder.Model.Gym;
import com.example.gymfinder.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    private Button updateBtn;
    private ProgressBar uploadProgressBar;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private EditText nameEditText2;
    private EditText descriptionEditText2;
    private ValueEventListener mDBListener;
    private List<Gym> mGyms;
    private RecyclerAdapter mAdapter;
    List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        nameEditText2 = (EditText) findViewById(R.id.nameEditText2);
        descriptionEditText2 = (EditText) findViewById(R.id.descriptionEditText2);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("gyms_uploads");

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list = new ArrayList<String>();
                for (DataSnapshot gymSnapshot : dataSnapshot.getChildren()) {

                    list.add(gymSnapshot.getKey());
                    Log.d("MyTag",""+gymSnapshot.getKey());

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UpdateActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        updateBtn = findViewById(R.id.updateBtn);
        uploadProgressBar = findViewById(R.id.progress_bar);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("gyms_uploads");

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(UpdateActivity.this, "An Upload is Still in Progress", Toast.LENGTH_SHORT).show();
                } else {
                    updateFile();
                }
            }
        });
    }



    private void updateFile() {
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
            Gym upload = new Gym(nameEditText2.getText().toString().trim(),

                    descriptionEditText2.getText ().toString ());

            String uploadId = mDatabaseRef.push().getKey();

            String UploadID = list.get(ItemsActivity.getPosition());
            mDatabaseRef.child(UploadID).setValue(upload);
            uploadProgressBar.setVisibility(View.INVISIBLE);
        }
        {
            Toast.makeText(this, "Gym Review Was UPDATED Sucesfully", Toast.LENGTH_SHORT).show();
        }

    }}




