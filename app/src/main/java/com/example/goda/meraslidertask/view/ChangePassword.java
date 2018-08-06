package com.example.goda.meraslidertask.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.goda.meraslidertask.R;

import butterknife.BindView;

public class ChangePassword extends AppCompatActivity {

    @BindView(R.id.login_btn)
    Button changePassword_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
    }

    public void change(View view) {
        Toast.makeText(ChangePassword.this,"toast", Toast.LENGTH_LONG).show();
    }
}
