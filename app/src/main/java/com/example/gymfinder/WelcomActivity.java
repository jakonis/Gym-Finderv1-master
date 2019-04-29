package com.example.gymfinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gymfinder.View.ItemsActivity;
import com.example.gymfinder.View.UploadActivity;
import com.example.gymfinder.gymfindersearchplaces.MapsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;




public class WelcomActivity extends AppCompatActivity {

    private Button openTeachersActivityBtn,openUploadActivityBtn;


    private static final String TAG = "MainActivity";
    private Button signOut;
    private TextView email;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    private Button btnAdd, btnViewData, openMapsActivityBtn;
    private EditText editText,editText2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
//get firebase auth instance
        auth = FirebaseAuth.getInstance();
        email = (TextView) findViewById(R.id.useremail);



        openTeachersActivityBtn = findViewById ( R.id.openGymsActivityBtn );
        openUploadActivityBtn = findViewById ( R.id.openUploadActivityBtn );
        openMapsActivityBtn = findViewById ( R.id.openMapActivityBtn );

        openTeachersActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WelcomActivity.this, ItemsActivity.class);
                startActivity(i);
            }
        });

        openUploadActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WelcomActivity.this, UploadActivity.class);
                startActivity(i);
            }
        });
        openMapsActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WelcomActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });







        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(user);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(WelcomActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };



        signOut = (Button) findViewById(R.id.sign_out);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });







    }




    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }





    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {

        email.setText("User Email: " + user.getEmail());


    }

    // this listener will be called when there is change in firebase user session
    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(WelcomActivity.this, LoginActivity.class));
                finish();
            } else {
                setDataToView(user);

            }
        }


    };

    //sign out method
    public void signOut() {
        auth.signOut();


// this listener will be called when there is change in firebase user session
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(WelcomActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
