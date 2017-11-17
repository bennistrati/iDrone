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

        Button button_settings;
        button_settings = (Button) findViewById(R.id.button_settings);

        Button button_start;
        button_start = (Button) findViewById(R.id.button_start);

        button_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                launchSettings();
            }
        });

        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                launchThrow();
            }
        });

    }

    private void launchSettings(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void launchThrow(){
        Intent intent = new Intent(this, ThrowActivity.class);
        startActivity(intent);
    }

}


