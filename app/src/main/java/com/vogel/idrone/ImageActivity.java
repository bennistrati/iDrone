package com.vogel.idrone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.idrone.R;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class ImageActivity extends AppCompatActivity {

    private ImageView mImage;
    private Bitmap mBitmap;
    private File[] listOfImages;
    private int currentImage;
    private ImageButton last;
    private ImageButton next;
    private File mediaStorageDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        //Initialize the two ImageButtons and the ImageView
        last = (ImageButton) findViewById(R.id.last);
        next = (ImageButton) findViewById(R.id.next);
        mImage = (ImageView) findViewById(R.id.image);

        //Load all Files in the Media Directory
        listOfImages = getImageFiles();

        //Make back Button invisible if the first image is displayed
        currentImage = listOfImages.length-1;
        next.setVisibility(View.INVISIBLE);
        if (listOfImages.length == 1){
            last.setVisibility(View.INVISIBLE);
        }

        //If there are no files, go back to MainActivity, else display first Image
        if (listOfImages.length == 0){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(), "Keine Bilder gefunden.", Toast.LENGTH_SHORT).show();
        } else {
            mBitmap = BitmapFactory.decodeFile(listOfImages[currentImage].getAbsolutePath());
            mImage.setImageBitmap(mBitmap);
        }

    }

    public void nextImage(View view){
        //Increment currentImage, check if its the last image and make next Button invisible if thats the case
        currentImage++;
        if (currentImage == listOfImages.length-1) {
            next.setVisibility(View.INVISIBLE);
        }
        last.setVisibility(View.VISIBLE);

        //change displayed Image
        mBitmap = BitmapFactory.decodeFile(listOfImages[currentImage].getAbsolutePath());
        mImage.setImageBitmap(mBitmap);
    }

    public void lastImage(View view){
        //Decrement currentImage, check if its the first image and make the last Button invisible if thats the case
        currentImage--;
        if (currentImage == 0){
            last.setVisibility(View.INVISIBLE);
        }
        next.setVisibility(View.VISIBLE);

        //change displayed Image
        mBitmap = BitmapFactory.decodeFile(listOfImages[currentImage].getAbsolutePath());
        mImage.setImageBitmap(mBitmap);
    }

    public void back(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_forward_in, R.anim.slide_forward_out);
        finish();
    }

    public void shareImage(View view){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                mBitmap, "Title", null);
        Uri imageUri =  Uri.parse(path);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, "Select"));
    }

    public void deleteImage(View view){
        listOfImages[currentImage].delete();
        Toast.makeText(getApplicationContext(), "Bild gelöscht.", Toast.LENGTH_SHORT).show();

        //reload ImageArray and decrement current Image
        listOfImages = getImageFiles();
        currentImage--;
        //prevent App from crashing it currentImage drops below zero
        if (currentImage == -1){
            currentImage = 0;
        }

        if (listOfImages.length == 1){
            next.setVisibility(View.INVISIBLE);
            last.setVisibility(View.INVISIBLE);
        }

        //If there are no files left, go back to MainActivity, else display Image before the deleted one
        if (listOfImages.length == 0){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(), "Keine Bilder mehr übrig.", Toast.LENGTH_SHORT).show();
        } else {
            mBitmap = BitmapFactory.decodeFile(listOfImages[currentImage].getAbsolutePath());
            mImage.setImageBitmap(mBitmap);
        }
    }

    private File[] getImageFiles() {
        mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "iDrone");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        File[] images = mediaStorageDir.listFiles();
        return images;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_forward_in, R.anim.slide_forward_out);
        finish();
    }
}
