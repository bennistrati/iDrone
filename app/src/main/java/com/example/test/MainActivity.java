package com.example.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/* Usable xml-elements in this layout:
Start-Button: ID: button_start
Settings-Button: ID: button_settings
Gallery-Button: ID: button_gallery
*/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Method to switch to Settings
    public void launchSettings(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    //Method to initialize throw
    public void launchThrow(View view){
        Intent intent = new Intent(this, ThrowActivity.class);
        startActivity(intent);
    }

}


