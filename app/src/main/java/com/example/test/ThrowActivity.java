package com.example.test;

import android.content.pm.PackageManager;
import android.content.Intent;
import android.hardware.Sensor;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.provider.MediaStore.Files.FileColumns;
import android.util.Log;

import java.io.File;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
/* Usable xml-elements in this layout:
Back-Button: ID: button_backThrow;
*/

public class ThrowActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    public static final String EXTRA_MESSAGE ="com.example.iDrone.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_throw);
        senSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /* Check if this device has a camera */
    private boolean checkCameraHardware(Context context){
        if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            //this device has a camera
            return true;
        }else{
            //no camera detected
            return false;
        }
    }
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();// attempt to get a camera isntance, camera.open(int) for accesing specific
        }catch(Exception e){
            //Camera is not available (in use or doesnt exist)
        }
        return c; // returns null if camera is unavailable
    }
    private PictureCallback mPicture = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                Log.d(TAG, "Error creating media file, check storage permissions: " +
                        e.getMessage());
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };








    //safes accelerometer data in x,y,z
    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        Sensor mySensor = sensorEvent.sensor;
        //turning point
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
            //x,y,z des accelerometers
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];


        }
    }

    //accuracy des accelerometer sensors
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }
    //pausiert sensor listener wenn app in hintergrund
    protected void onPause(){
        super.onPause();
        senSensorManager.unregisterListener(this);
    }
    //reaktiviert sensor listener wenn app wieder gestartet
    protected void onResume(){
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }


    //Method to return to the main menu
    public void returnToMenuFromThrow(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
