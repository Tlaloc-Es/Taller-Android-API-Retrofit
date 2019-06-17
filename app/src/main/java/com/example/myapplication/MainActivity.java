package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ImageView imgComick;
    TextView txtvTitle;
    Button btnNewComick;
    XkcdService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgComick = findViewById(R.id.imgComick);
        txtvTitle = findViewById(R.id.txtvTitle);
        btnNewComick = findViewById(R.id.btnNewComick);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://xkcd.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(XkcdService.class);

        btnNewComick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Call<Xkcd> call = service.getComic(new Random().nextInt(1000));

                call.enqueue(new Callback<Xkcd>() {

                    @Override
                    public void onResponse(Call<Xkcd> call, Response<Xkcd> response){

                        Xkcd xkcd = response.body();

                        try {
                            if(xkcd != null) {

                                txtvTitle.setText(xkcd.getTitle());

                                Picasso.with(MainActivity.this)
                                        .load(xkcd.getImg())
                                        .into(imgComick);
                            }
                        }catch (Exception e){
                            Log.e("Error", e.toString());
                        }

                    }

                    @Override
                    public void onFailure(Call<Xkcd> call, Throwable throwable){
                        Log.e("Error", throwable.getMessage());
                    }

                });            }
        });

    }

}
