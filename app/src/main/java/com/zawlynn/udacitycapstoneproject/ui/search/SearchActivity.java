package com.zawlynn.udacitycapstoneproject.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.databinding.MainActivityBindingNew;
import com.zawlynn.udacitycapstoneproject.pojo.Genre;
import com.zawlynn.udacitycapstoneproject.ui.main.event.OnNetworkStateListener;
import com.zawlynn.udacitycapstoneproject.ui.main.fragment.FavouriteFragment;
import com.zawlynn.udacitycapstoneproject.ui.main.fragment.HomeFragment;
import com.zawlynn.udacitycapstoneproject.ui.main.fragment.LibraryFragment;
import com.zawlynn.udacitycapstoneproject.ui.main.fragment.SearchFragment;
import com.zawlynn.udacitycapstoneproject.utils.Constant;

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
