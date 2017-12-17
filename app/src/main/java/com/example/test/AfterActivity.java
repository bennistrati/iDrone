package com.example.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/* Usable xml-elements in this layout:
Back-Button: ID: button_backAfter
Final-Image: ID: image_final
Save-Button: ID: button_save
Delete-Button: ID: button_delete
Velocity-Text: ID: text_velocity
Time-Text: ID: text_time
Height-Text: ID: text_height
*/

public class AfterActivity extends AppCompatActivity {

    public ImageView mImageView;
    public Bitmap mImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after);

        //byte[] byteArray = getIntent().getByteArrayExtra("EXTRA_BITMAP");
        //mImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        mImageView = (ImageView) findViewById(R.id.image_final);
        //mImageView.setImageBitmap(mImage);

    }

    //Method to Return to the main menu
    public void returnToMenuFromAfter(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
