package com.example.gymfinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymfinder.View.InformationActivity;
import com.example.gymfinder.View.ItemsActivity;
import com.example.gymfinder.View.UpdateActivity;
import com.example.gymfinder.View.UploadActivity;
import com.example.gymfinder.gymfindersearchplaces.MapsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AcitvityWelcome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button openTeachersActivityBtn,openUploadActivityBtn;
    private static final String TAG = "MainActivity";
    private TextView email,googleemail;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private Button btnAdd, btnViewData, openMapsActivityBtn;
    private EditText editText,editText2;



    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {

        email.setText("User Email: " + user.getEmail());

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acitvity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        email = (TextView) findViewById(R.id.useremail);
        openTeachersActivityBtn = findViewById ( R.id.openGymsActivityBtn );
        openUploadActivityBtn = findViewById ( R.id.openUploadActivityBtn );
        openMapsActivityBtn = findViewById ( R.id.openMapActivityBtn );






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        openTeachersActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AcitvityWelcome.this, ItemsActivity.class);
                startActivity(i);
            }
        });

        openUploadActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AcitvityWelcome.this, UploadActivity.class);
                startActivity(i);
            }
        });
        openMapsActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AcitvityWelcome.this, MapsActivity.class);
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
                    startActivity(new Intent(AcitvityWelcome.this, LoginActivity.class));
                    finish();
                }
            }
        };



        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

    }





    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(AcitvityWelcome.this, LoginActivity.class));
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
                    startActivity(new Intent(AcitvityWelcome.this, LoginActivity.class));
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






















    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.acitvity_welcome, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();





        if (id == R.id.nav_map) {
            // Handle the camera action

            Intent i = new Intent(AcitvityWelcome.this, MapsActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_desc) {

            Intent i = new Intent(AcitvityWelcome.this, UploadActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_review) {

            Intent i = new Intent(AcitvityWelcome.this, ItemsActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_info) {

            Intent i = new Intent(AcitvityWelcome.this, InformationActivity.class);
            startActivity(i);


        } else if (id == R.id.nav_logoff) {

            auth.signOut();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
