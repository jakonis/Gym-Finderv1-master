package com.example.gymfinder.gymfindersearchplaces;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.gymfinder.R;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.example.gymfinder.gymfindersearchplaces.model.MyPlaceDetail;
import com.example.gymfinder.gymfindersearchplaces.remote.IGoogleService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button button;
    private ImageView imageView;
    private IGoogleService service;
    private TextView placeName;
    private TextView openingH;
    private TextView closingHr;
    private TextView place_address;
    private RatingBar ratingBar;
    private Toolbar toolbar;

    MyPlaceDetail detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar=findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        service=Common.getGoogleService();
        placeName=findViewById(R.id.placeName);
        ratingBar=findViewById(R.id.ratingBar);
        openingH=findViewById(R.id.openingH);
        place_address=findViewById(R.id.place_detail);
        placeName=findViewById(R.id.placeName);



        imageView=findViewById(R.id.imageView);
        ProgressDialog dialog=new ProgressDialog(MainActivity.this);
        dialog.setMessage("wait");
        dialog.show();
        if(Common.currentResult!=null&&!TextUtils.isEmpty(String.valueOf(Common.currentResult))&&Common.currentResult.getPhotos()!=null){
            Picasso.get().load(getUrl(Common.currentResult.getPhotos()[0].getPhoto_reference())).placeholder(R.drawable.ic_image_black_24dp).networkPolicy(NetworkPolicy.OFFLINE).into(imageView, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load(getUrl(Common.currentResult.getPhotos()[0].getPhoto_reference())).placeholder(R.drawable.ic_image_black_24dp).into(imageView);
                }


                public void onError() {
                    Picasso.get().load(getUrl(Common.currentResult.getPhotos()[0].getPhoto_reference())).placeholder(R.drawable.ic_image_black_24dp).into(imageView);

                }
            });
        }

        if(Common.currentResult.getRating()!=null&&!TextUtils.isEmpty(Common.currentResult.getRating())){
            ratingBar.setRating(Float.parseFloat(Common.currentResult.getRating()));
        }
        else {
            ratingBar.setVisibility(View.INVISIBLE);
        }
        if(Common.currentResult.getOpening_hours()!=null){

            openingH.setText("open now: "+ String.valueOf(Common.currentResult.getOpening_hours().getOpen_now()));
        }

        service.getPlaceDetail(getPlaceDetailUrl(Common.currentResult.getPlace_id())).enqueue(new Callback<MyPlaceDetail>() {
            @Override
            public void onResponse(Call<MyPlaceDetail> call, Response<MyPlaceDetail> response) {

                detail=response.body();
                placeName.setText(detail.getResult().getName());
                getSupportActionBar().setTitle(detail.getResult().getName());
                place_address.setText(detail.getResult().getFormatted_address());

            }

            @Override
            public void onFailure(Call<MyPlaceDetail> call, Throwable t) {

            }
        });
        dialog.dismiss();
    }

    private String getPlaceDetailUrl(String place_id) {

        StringBuilder builder=new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
        builder.append("placeid="+place_id);
        builder.append("&key=AIzaSyDIsZdb4NvUnv1er3SenoAKvi9kcSDMci8");
        return builder.toString();
    }

    private String getUrl(String ref) {

        StringBuilder builder=new StringBuilder("https://maps.googleapis.com/maps/api/place/photo?");
        builder.append("maxwidth=1000");
        builder.append("&photoreference="+ref);
        builder.append("&key=AIzaSyDIsZdb4NvUnv1er3SenoAKvi9kcSDMci8");
        return builder.toString();
    }




}
