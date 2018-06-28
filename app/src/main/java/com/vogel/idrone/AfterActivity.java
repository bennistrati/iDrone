package com.vogel.idrone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.idrone.R;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class AfterActivity extends AppCompatActivity {

    //Variables to the the final Image
    private ImageView finalImage;
    private String filePath;
    private File imgFile;
    private Bitmap imgBitmap;

    /**
     * Get filePath to the final Image and load Image to ImageView onCreate
     * @param savedInstanceState
     */
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
            imgBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            finalImage.setImageBitmap(imgBitmap);
        }
    }

    /**
     * Method to delete Image
     * @param view
     */
    public void deleteImage(View view){
        imgFile.delete();
        Toast.makeText(getApplicationContext(), "Bild gelöscht.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_back_in, R.anim.slide_back_out);
        finish();
    }

    /**
     * Method to go back to the Main Menu
     * @param view
     */
    public void back(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_forward_in, R.anim.slide_forward_out);
        finish();
    }

    /**
     * Method to share the Image
     * @param view
     */
    public void shareImage(View view){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                imgBitmap, "Title", null);
        Uri imageUri =  Uri.parse(path);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, "Select"));
    }

    /**
     * Go back to MainActivity if back button is pressed
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_forward_in, R.anim.slide_forward_out);
        finish();
    }
}
