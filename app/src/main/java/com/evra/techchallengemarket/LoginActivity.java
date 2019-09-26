package com.evra.techchallengemarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText etUserName, etPassword;
    Switch switchRem;
    Button btnLogin;
    Context context = this;
    UserSessionManagerPrefs sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new UserSessionManagerPrefs(getApplicationContext());

        if (sessionManager.isLoggedIn()){
            loggedIn();
            finish();
        }

        init();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUserName.getText().toString();
                String pass = etPassword.getText().toString();
                if (user.equalsIgnoreCase("kariyer") && pass.equals("2019ADev")){
                    if (switchRem.isChecked()){
                        sessionManager.setSession();
                        loggedIn();
                    }else {
                        sessionManager.setForgetSession();
                        loggedIn();
                    }
                    Toast.makeText(context, R.string.toast_success, Toast.LENGTH_SHORT).show();
                }else if (user.equalsIgnoreCase("") || pass.equals("")){
                    Toast.makeText(context, R.string.toast_empty_field, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, R.string.toast_wrong_field, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void init(){
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        switchRem = findViewById(R.id.switchRememberMe);
        btnLogin = findViewById(R.id.btnLogin);
    }
    private void loggedIn(){
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
