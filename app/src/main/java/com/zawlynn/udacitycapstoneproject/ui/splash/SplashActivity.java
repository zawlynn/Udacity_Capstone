package com.zawlynn.udacitycapstoneproject.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zawlynn.udacitycapstoneproject.PodcastApplication;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.ui.login.LoginActivity;
import com.zawlynn.udacitycapstoneproject.ui.main.HomeActivity;

import javax.inject.Inject;

public class SplashActivity extends AppCompatActivity {
    @Inject
    public FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        PodcastApplication.getInstance().getFirebaseComponent().inject(this);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (AccessToken.getCurrentAccessToken() != null||currentUser!=null){
           negativeToMain(HomeActivity.class);
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
