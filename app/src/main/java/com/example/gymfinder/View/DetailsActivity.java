package com.example.gymfinder.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.example.gymfinder.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;




public class DetailsActivity extends AppCompatActivity {

    TextView nameDetailTextView,descriptionDetailTextView,dateDetailTextView;


    private void initializeWidgets(){
        nameDetailTextView= findViewById(R.id.nameDetailTextView);
        descriptionDetailTextView= findViewById(R.id.descriptionDetailTextView);
        dateDetailTextView= findViewById(R.id.dateDetailTextView);

    }
    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initializeWidgets();

        //RECEIVE DATA FROM ITEMSACTIVITY VIA INTENT
        Intent i=this.getIntent();
        String name=i.getExtras().getString("NAME_KEY");
        String description=i.getExtras().getString("DESCRIPTION_KEY");
        String imageURL=i.getExtras().getString("IMAGE_KEY");

        //SET RECEIVED DATA TO TEXTVIEWS AND IMAGEVIEWS
        nameDetailTextView.setText(name);
        descriptionDetailTextView.setText(description);
        dateDetailTextView.setText("DATE: "+getDateToday());
    }

}
