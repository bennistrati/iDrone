package com.android.idrone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;

public class AfterActivity extends AppCompatActivity {

    private ImageView finalImage;
    private TextView textView;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after);

        Intent getIntent = getIntent();
        filePath = getIntent.getExtras().getString("filePath");

        textView = (TextView) findViewById(R.id.textView);
        textView.setText(filePath);

        finalImage = (ImageView) findViewById(R.id.finalImage);

        /*Activity activity = this;
        Glide.with(activity)
            .load("/storage/emulated/0/pictures/iDrone/IMG_20180111_184224.jpg")
            .into(finalImage);*/
    }

    public void showImage(View view){
        File imgFile = new File(filePath);

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            finalImage = (ImageView) findViewById(R.id.finalImage);
            finalImage.setImageBitmap(myBitmap);
        }
    }
}
