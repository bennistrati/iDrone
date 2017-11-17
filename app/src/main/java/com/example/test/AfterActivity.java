package com.example.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/* Usable xml-elements in this layout:
Back-Button: ID: button_backAfter
Final-Image: ID: image_final
Save-Button: ID: button_save
Delete-Button: ID: button_delete
Velocity-Text: ID: text_velocity
Time-Text: ID: text_time
Height-Text: ID: text_height
*/

public class AfterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after);

        Button button_backAfter;
        button_backAfter = (Button) findViewById(R.id.button_backAfter);

        button_backAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                launchMenu();
            }
        });
    }

    private void launchMenu(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}