package com.zawlynn.udacitycapstoneproject.ui.genre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.databinding.GenreBinding;
import com.zawlynn.udacitycapstoneproject.pojo.Genre;
import com.zawlynn.udacitycapstoneproject.ui.genre.adapter.GenreAdapter;
import com.zawlynn.udacitycapstoneproject.ui.genre.event.OnGenreClicked;
import com.zawlynn.udacitycapstoneproject.ui.genre.viewmodel.GenreViewModel;
import com.zawlynn.udacitycapstoneproject.ui.onboard.OnBoardActivity;

public class GenreActivity extends AppCompatActivity implements OnGenreClicked{
    GenreViewModel viewModel;
    GenreBinding binding;
    GenreAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_genre);
        viewModel= ViewModelProviders.of(this).get(GenreViewModel.class);
        initUI();
        viewModel.getGenresLiveData().observe(this,genres -> {
            adapter.submitList(genres);
        });

    }
    private void initUI(){
        binding.recGenre.setLayoutManager( new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        adapter=new GenreAdapter(this,false);
        binding.recGenre.setAdapter(adapter);
    }
    public void onNext(View view){
        Intent i=new Intent(this, OnBoardActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onGenreClicked(Genre genre) {
        viewModel.onGenreStatus(genre);
    }
}
