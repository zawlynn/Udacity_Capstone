package com.zawlynn.udacitycapstoneproject.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.ui.login.LoginActivity;
import com.zawlynn.udacitycapstoneproject.ui.main.MainActivity;

import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (AccessToken.getCurrentAccessToken() != null){
           negativeToMain(MainActivity.class);
        } else {
            negativeToMain(LoginActivity.class);
        }
    }
    public void negativeToMain(Class activity){
        Intent i=new Intent(this,activity);
        startActivity(i);
        finish();
    }
}
