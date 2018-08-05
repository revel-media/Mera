package com.example.goda.meraslidertask.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.goda.meraslidertask.R;
import com.example.goda.meraslidertask.utils.PreferencesUtils;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        PreferencesUtils.deleteEmail(this);
        //Splash screen handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                String email = PreferencesUtils.getEmail(MainActivity.this);
                if (email != null && email != ""){
                    Toast.makeText(MainActivity.this,"already logged in", Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(MainActivity.this, ClientHome.class);
                    startActivity(intent);
                }else {
                    Intent intent =new Intent(MainActivity.this, FirstWelcomeSlider.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this,"new login", Toast.LENGTH_LONG).show();
                }

              /*  Intent intent = new Intent(MainActivity.this, FirstWelcomeSlider.class);
                startActivity(intent);*/
                finish();
            }
        },SPLASH_TIME_OUT);

    }

}
