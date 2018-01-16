package com.android.idrone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class AfterActivity extends AppCompatActivity {

    private ImageView finalImage;
    private String filePath;
    private File imgFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after);

        //Get Info passed from intent
        Intent getIntent = getIntent();
        filePath = getIntent.getExtras().getString("filePath");

        //Load Image to Imageview
        finalImage = (ImageView) findViewById(R.id.finalImage);
        imgFile = new File(filePath);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            finalImage = (ImageView) findViewById(R.id.finalImage);
            finalImage.setImageBitmap(myBitmap);
        }

        /*Activity activity = this;
        Glide.with(activity)
            .load("/storage/emulated/0/pictures/iDrone/IMG_20180111_184224.jpg")
            .into(finalImage);*/
    }

    public void deleteImage(View view){
        imgFile.delete();
        Intent intent = new Intent(this, ThrowActivity.class);
        startActivity(intent);
        finish();
    }

    public void back(View view){
        Intent intent = new Intent(this, ThrowActivity.class);
        startActivity(intent);
        finish();
    }

    public void shareImage(View view){
        Bitmap b = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                b, "Title", null);
        Uri imageUri =  Uri.parse(path);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, "Select"));
    }
}
