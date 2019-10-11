package com.zawlynn.udacitycapstoneproject.ui.onboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.databinding.BindingOnBoard;
import com.zawlynn.udacitycapstoneproject.ui.main.MainActivity;
import com.zawlynn.udacitycapstoneproject.ui.onboard.viewmodel.OnBoardViewmodel;

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
                Intent i=new Intent(this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }else {
                Log.d(TAG, "onCreate: SUCCESS");
            }
        });
    }
}
