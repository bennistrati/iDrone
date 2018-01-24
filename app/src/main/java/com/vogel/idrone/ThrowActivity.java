package com.vogel.idrone;

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

import com.android.idrone.R;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) //App requires at least Android Lollipop
public class ThrowActivity extends AppCompatActivity
        implements SensorEventListener, ActivityCompat.OnRequestPermissionsResultCallback, CameraHelper {

    //Sensor Variables
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private boolean isFlying;

    //Camera Variables
    CameraController cameraController;
    AutoFitTextureView textureView;

    /**
     * Initialize the Sensor Manager and the textureView, start the CameraController and request Permissions onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_throw);

        //This is where we initialize the Sensor Manager and start a Listener on the Accelerometer
        senSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer= senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        isFlying = false;

        //This is where we initialize the Texture View and start the Preview on it/initialize the Camera Controller
        textureView = (AutoFitTextureView)findViewById(R.id.textureview);
        cameraController = new CameraController(ThrowActivity.this, textureView, ThrowActivity.this);

        //If permissions aren't granted yet, request them
        getPermissions();
    }

    /**
     * Method to get requested permissions
     */
    private void getPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                //Requesting permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    /**
     * Restart Activity (and therefore the Camera Controller) if Permissions get granted
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission granted
                    Intent intent = new Intent(this, ThrowActivity.class);
                    finish();
                    startActivity(intent);
                }
                return;
            }
        }
    }

    /**
     * Saves Accelerometer data in x,y,z and shoots image
     * @param sensorEvent
     */
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
                    if(cameraController != null) {
                        cameraController.takePicture();
                        isFlying = false;
                    }
                }
            }
        }

    }

    /**
     * This method is just an override to empty the onAccuracyChanged Method
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Asynchronous Change to After Activity as soon as the file gets saved
     * @param filePath Path to the saved File
     */
    @Override
    public void fileSaved(String filePath) {
        Intent intent = new Intent(this, AfterActivity.class);
        intent.putExtra("filePath", filePath);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        finish();
    }

    /**
     * Go back to MainActivity if the 'back' button is pressed
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        finish();
    }
}
