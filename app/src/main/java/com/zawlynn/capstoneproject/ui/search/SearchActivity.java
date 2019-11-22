package com.zawlynn.capstoneproject.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.databinding.MainActivityBindingNew;
import com.zawlynn.capstoneproject.pojo.Genre;
import com.zawlynn.capstoneproject.ui.main.event.OnNetworkStateListener;
import com.zawlynn.capstoneproject.ui.main.fragment.HomeFragment;
import com.zawlynn.capstoneproject.ui.main.fragment.SearchFragment;
import com.zawlynn.capstoneproject.utils.Constant;

public class SearchActivity extends AppCompatActivity implements OnNetworkStateListener {
    MainActivityBindingNew binding;
    Genre genre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main_v2);
        genre=getIntent().getParcelableExtra(Constant.DATA);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SearchFragment.newInstance(genre))
                    .addToBackStack(HomeFragment.class.getName())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public void isNetworkAvailable(boolean staus) {
        if(!staus){
            Snackbar.make(binding.container,getResources().getString(R.string.no_internet),
                    Snackbar.LENGTH_SHORT).show();
        }
    }
}
