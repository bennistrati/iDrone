package com.example.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/* Usable xml-elements in this layout:
Back-Button: ID: button_back
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
}
