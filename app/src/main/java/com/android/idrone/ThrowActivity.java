package com.android.idrone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ThrowActivity extends AppCompatActivity
        implements SensorEventListener, ActivityCompat.OnRequestPermissionsResultCallback, CameraHelper {

    /**
     * Sensor Variables
     */
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private boolean isFlying;

    private String filePath;

    CameraControllerV2WithPreview ccv2WithPreview;
    CameraControllerV2WithoutPreview ccv2WithoutPreview;

    AutoFitTextureView textureView;
    Switch startstoppreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Initialize SensorManager
         */
        senSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer= senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        isFlying = false;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent intent = getIntent();

        boolean showpreview = intent.getBooleanExtra("showpreview", true);

        textureView = (AutoFitTextureView)findViewById(R.id.textureview);
        startstoppreview = (Switch) findViewById(R.id.startstoppreview);

        if(showpreview) {
            ccv2WithPreview = new CameraControllerV2WithPreview(ThrowActivity.this, textureView, ThrowActivity.this);
            startstoppreview.setChecked(true);
        } else {
            ccv2WithoutPreview = new CameraControllerV2WithoutPreview(getApplicationContext());
            startstoppreview.setChecked(false);
        }


        startstoppreview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(startstoppreview.isChecked()) {
                    intent.putExtra("showpreview", true);
                    finish();
                    startActivity(intent);

                } else {
                    intent.putExtra("showpreview", false);
                    finish();
                    startActivity(intent);
                }
            }
        });

        findViewById(R.id.getpicture).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(startstoppreview.isChecked() && ccv2WithPreview != null) {
                    ccv2WithPreview.takePicture();
                } else if(ccv2WithoutPreview != null){
                    ccv2WithoutPreview.openCamera();
                    try { Thread.sleep(20); } catch (InterruptedException e) {}
                    ccv2WithoutPreview.takePicture();
                }

                Toast.makeText(getApplicationContext(), "Picture Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        getPermissions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(ccv2WithPreview != null) {
//            ccv2WithPreview.closeCamera();
//        }
//        if(ccv2WithoutPreview != null) {
//            ccv2WithoutPreview.closeCamera();
//        }
    }

    private void getPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                //Requesting permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override //Override from ActivityCompat.OnRequestPermissionsResultCallback Interface
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                }
                return;
            }
        }
    }

    //Saves Accelerometer data in x,y,z, shoots image and triggers Activity Change
    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        Sensor mySensor = sensorEvent.sensor;
        //turning point
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
            //x,y,z des accelerometers
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            float move = Math.abs(x) + Math.abs(y) + Math.abs(z);
            if(!isFlying){
                if(move > 50){
                    isFlying = true;
                }
            } else if (isFlying){
                if(move < 1.3){
                    if(startstoppreview.isChecked() && ccv2WithPreview != null) {
                        ccv2WithPreview.takePicture();
                        isFlying = false;
                    } else if(ccv2WithoutPreview != null){
                        ccv2WithoutPreview.openCamera();
                        try { Thread.sleep(20); } catch (InterruptedException e) {}
                        ccv2WithoutPreview.takePicture();
                        isFlying = false;
                    }
                }
            }
        }

    }

    //This method is just an override to empty the onAccuracyChanged Method
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //Pause sensor listener if App goes to background
    protected void onPause(){
        super.onPause();
        senSensorManager.unregisterListener(this);
    }
    //Reactivate sensor listener if App comes back to foreground
    protected void onResume(){
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void fileSaved(String filePath) {
        Intent intent = new Intent(this, AfterActivity.class);
        intent.putExtra("filePath", filePath);
        startActivity(intent);
        finish();
    }
}
