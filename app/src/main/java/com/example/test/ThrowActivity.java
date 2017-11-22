package com.example.test;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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




    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        Sensor mySensor = sensorEvent.sensor;

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
