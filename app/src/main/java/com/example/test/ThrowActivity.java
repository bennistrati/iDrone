package com.example.test;

import android.Manifest;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.hardware.camera2.*;
import android.widget.TextView;


/* Usable xml-elements in this layout:
Back-Button: ID: button_backThrow;
*/

public class ThrowActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private boolean isFlying;

    public TextView text = findViewById(R.id.text);
    public CameraManager mCamera;
    public String[] cameraIDs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_throw);

        senSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        isFlying = false;
        try {
            cameraIDs = mCamera.getCameraIdList();
            text.setText(cameraIDs[0]);
        } catch (CameraAccessException e) {
            text.setText("Sorry, couldn't find CameraDevice.");
        }
    }

    //Saves Accelerometer data in x,y,z and triggers Activity Change
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
                    launchAfterActivity();
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

    //Method to return to main menu
    public void returnToMenuFromThrow(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //Method to go to the after activity
    public void launchAfterActivity(){


        Intent intent = new Intent(this, AfterActivity.class);
        startActivity(intent);
        finish();
    }
}
