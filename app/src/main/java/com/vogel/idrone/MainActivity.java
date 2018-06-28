package com.vogel.idrone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.idrone.R;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) //App requires at least Android Lollipop
public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPermissions();
    }

    /**
     * Method to launch ThrowActivity
     * @param view
     */
    public void launchThrow(View view){
        Intent intent = new Intent(this, ThrowActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_forward_in,R.anim.slide_forward_out);
        finish();
    }

    public void launchGallery(View view){
        Intent intent = new Intent(this, ImageActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_forward_in,R.anim.slide_forward_out);
        finish();
    }


    private void getPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                //Requesting permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission granted
                } else {
                    finish();
                    Toast.makeText(getApplicationContext(), "Ohne Berechtigungen keine iDrone!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
