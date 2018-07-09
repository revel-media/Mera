package com.example.goda.meraslidertask.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.goda.meraslidertask.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterationConditions extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.conditionsCheckBox) CheckBox checkBox;
    @BindView(R.id.registration_next_button)  Button next;
    private boolean checked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration_conditions);
        ButterKnife.bind(this);

        checkBox.setOnClickListener(this);
        next.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == checkBox) {
            if (checkBox.isChecked()) {
                checked = true;
            } else {
                checked = false;
            }
        }else if (view == next){
            if(checked) {
                Intent intent = new Intent(RegisterationConditions.this, RegistrationMainDataEntry.class);
                startActivity(intent);
            }else {
                Toast.makeText(this,"يجب الموافقه على شروط التسجيل اولا", Toast.LENGTH_LONG).show();
            }
        }
    }
}
