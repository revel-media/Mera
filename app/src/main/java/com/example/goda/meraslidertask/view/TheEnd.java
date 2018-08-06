package com.example.goda.meraslidertask.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.goda.meraslidertask.R;
import com.example.goda.meraslidertask.utils.PreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TheEnd extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.client_data_circle_tv)
    TextView userAccountType;
    @BindView(R.id.clientTypeButton)
    Button clientType;

    private String accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_end);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.newUserTitle);

        accountType = PreferencesUtils.getAccountType(TheEnd.this);
        if (accountType.matches("1")){
            userAccountType.setText("بيانات العميل");
        }else {

            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) userAccountType.getLayoutParams();
            LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) clientType.getLayoutParams();
            lp.setMargins(0,0,0,0);
            lp2.setMargins(0,0,0,0);

            userAccountType.setLayoutParams(lp);
            clientType.setLayoutParams(lp2);
            userAccountType.setText("بيانات مقدم الخدمه");
        }

    }

    public void next(View view) {

        if (accountType.matches("1")){
            Intent intent = new Intent(TheEnd.this, ClientHome.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(TheEnd.this, ServiceProviderHome.class);
            startActivity(intent);
        }
    }
}
