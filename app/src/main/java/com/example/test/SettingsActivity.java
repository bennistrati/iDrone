package com.example.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        Button button_backSet;
        button_backSet = (Button) findViewById(R.id.button_backSet);

        button_backSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                launchMenu();
            }
        });
    }
}

    private void launchMenu(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }