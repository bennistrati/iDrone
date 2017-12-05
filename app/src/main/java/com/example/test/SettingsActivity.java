package com.example.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/* Usable xml-elements in this layout:
Back-Button: ID: button_backSet
Sound-ToggleButton: ID: button_sound
Camera-ToggleButton: ID: button_camera
Sound-Text: ID: text_sound
Camera-Text: ID: text_camera
*/

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    //Method to return to the main menu
    public void returnToMenuFromSettings(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        finish();
    }
}