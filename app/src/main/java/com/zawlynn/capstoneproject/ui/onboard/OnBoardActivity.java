package com.zawlynn.capstoneproject.ui.onboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zawlynn.capstoneproject.ui.main.HomeActivity;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.databinding.BindingOnBoard;
import com.zawlynn.capstoneproject.ui.onboard.viewmodel.OnBoardViewmodel;

public class OnBoardActivity extends AppCompatActivity {
    OnBoardViewmodel viewmodel;
    BindingOnBoard binding;
    private static final String TAG = "OnBoardActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_setting_up);
        viewmodel= ViewModelProviders.of(this).get(OnBoardViewmodel.class);
        viewmodel.isSuccess().observe(this,status->{
            if(status){
                viewmodel.requestCuratedPodcast(getApplicationContext());
            }else {
                Log.d(TAG, "onCreate: UNSUCCESS");
            }
        });
        viewmodel.isComplete().observe(this, status->{
            if(status){
                Intent i=new Intent(this, HomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }else {
                Log.d(TAG, "onCreate: SUCCESS");
            }
        });
    }
}
