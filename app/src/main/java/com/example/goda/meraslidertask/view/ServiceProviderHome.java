package com.example.goda.meraslidertask.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.goda.meraslidertask.R;
import com.example.goda.meraslidertask.utils.PreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceProviderHome extends AppCompatActivity {
    @BindView(R.id.logout)
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_home);
        ButterKnife.bind(this);
    }

    public void logOut(View view) {
        PreferencesUtils.deleteEmail(ServiceProviderHome.this);
    }
}
