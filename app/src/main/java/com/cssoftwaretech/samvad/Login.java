package com.cssoftwaretech.samvad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    EditText etUserName, etPassword;
    Button btnLogin;
    TextView tv_signUp;
    Context context = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initialization();
        events();
    }

    private void initialization() {
        etUserName = (EditText) findViewById(R.id.login_et_username);
        etPassword = (EditText) findViewById(R.id.login_et_password);
        btnLogin = (Button) findViewById(R.id.login_btn_login);
        tv_signUp = (TextView) findViewById(R.id.login_tv_signUp);
    }

    private void events() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnLogin();
            }
        });
        tv_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           startActivity(new Intent(context, SignUp.class));
          //      startActivity(new Intent(context, AllSchoolList.class));
            }
        });
        etPassword.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startActivity(new Intent(context, MainActivity.class));
                return false;
            }
        });
    }

    public void OnLogin() {
        String username = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username, password);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
