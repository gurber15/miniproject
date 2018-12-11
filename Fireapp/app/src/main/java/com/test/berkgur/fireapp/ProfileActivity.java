package com.test.berkgur.fireapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.Random;


public class ProfileActivity extends AppCompatActivity {
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tv1=(TextView)findViewById(R.id.textViewVerified);
        int min = -10;
        int max = 10;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        tv1.setText("Today is  degrees celsius "+i1);
    }
}
