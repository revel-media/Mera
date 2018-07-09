package com.example.goda.meraslidertask.view;

import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.goda.meraslidertask.R;
import com.example.goda.meraslidertask.adapter.WelcomePagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstWelcomeSlider extends AppCompatActivity implements View.OnClickListener {

   @BindView(R.id.firstSlider) ViewPager viewPager;
   @BindView(R.id.loginButton) Button loginButton;
   @BindView(R.id.registerButton) Button registerButton;
   private int[] layouts = {
            R.drawable.merainfo1,
            R.drawable.merainfo2,
            R.drawable.merainfo3};
   private WelcomePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_welcome_slider);
        ButterKnife.bind(this);

        pagerAdapter = new WelcomePagerAdapter(layouts,this);
        viewPager.setAdapter(pagerAdapter);

        // transparent status bar
        /*if (Build.VERSION.SDK_INT >=19){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == loginButton){
            Intent intent = new Intent(FirstWelcomeSlider.this, LoginActivity.class);
            startActivity(intent);
        }
        else if(view == registerButton){
            Intent intent = new Intent(FirstWelcomeSlider.this, RegisterationConditions.class);
            startActivity(intent);
        }
    }
}
