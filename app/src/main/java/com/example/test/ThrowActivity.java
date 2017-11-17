package com.example.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/* Usable xml-elements in this layout:
Back-Button: ID: button_backThrow;
*/

public class ThrowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_throw);

        Button button_backThrow;
        button_backThrow = (Button) findViewById(R.id.button_backThrow);

        button_backThrow.setOnClickListener(new View.OnClickListener() {
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
