package com.example.gymfinder.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;
import java.util.List;

import com.example.gymfinder.Adapter.RecyclerAdapter;
import com.example.gymfinder.Model.Gym;
import com.example.gymfinder.R;

public class ItemsActivity extends AppCompatActivity implements RecyclerAdapter.OnItemClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Gym> mGyms;
    static int UpdatePos;

    private void openDetailActivity(String[] data){
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("NAME_KEY",data[0]);
        intent.putExtra("DESCRIPTION_KEY",data[1]);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_items );

        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager (this));

        mProgressBar = findViewById(R.id.myDataLoaderProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        mGyms = new ArrayList<> ();
        mAdapter = new RecyclerAdapter (ItemsActivity.this, mGyms);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(ItemsActivity.this);


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("gyms_uploads");

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mGyms.clear();

                for (DataSnapshot gymSnapshot : dataSnapshot.getChildren()) {
                    Gym upload = gymSnapshot.getValue(Gym.class);
                    upload.setKey(gymSnapshot.getKey());
                    mGyms.add(upload);
                }
                mAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ItemsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
    public void onItemClick(int position) {
        Gym clickedGym=mGyms.get(position);
        String[] GymData={clickedGym.getName(),clickedGym.getDescription()};
        openDetailActivity(GymData);
    }

    @Override
    public void onShowItemClick(int position) {
        Gym clickedGym=mGyms.get(position);
        String[] GymData={clickedGym.getName(),clickedGym.getDescription()};
        openDetailActivity(GymData);
    }

    @Override
    public void onDeleteItemClick(int position) {
        Gym selectedItem = mGyms.get(position);
        final String selectedKey = selectedItem.getKey();



                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(ItemsActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }

    @Override
    public void onUpdateItemClick(int position) {
        Gym clickedGym=mGyms.get(position);
        UpdatePos = position;
        Intent i = new Intent(ItemsActivity.this, UpdateActivity.class);
        startActivity(i);

    }

    public static int getPosition()
    {
        return UpdatePos;
    }


    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }

}
