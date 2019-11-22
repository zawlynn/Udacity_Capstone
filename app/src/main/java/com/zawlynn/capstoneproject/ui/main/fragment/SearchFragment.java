package com.zawlynn.capstoneproject.ui.main.fragment;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.databinding.BindingSearchFrag;
import com.zawlynn.capstoneproject.pojo.Genre;
import com.zawlynn.capstoneproject.pojo.Podcast;
import com.zawlynn.capstoneproject.ui.main.adapter.podcast.PodcastAdapter;
import com.zawlynn.capstoneproject.ui.main.event.OnNetworkStateListener;
import com.zawlynn.capstoneproject.ui.main.event.OnPodcastClick;
import com.zawlynn.capstoneproject.ui.podcast.PodcastActivity;
import com.zawlynn.capstoneproject.ui.search.viewmodel.SearchViewModel;
import com.zawlynn.capstoneproject.utils.Constant;
import com.zawlynn.capstoneproject.utils.NetworkUtils;

public class SearchFragment extends Fragment implements OnPodcastClick {
    private OnNetworkStateListener onNetworkStateListener;
    private SearchViewModel viewmodel;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;
    private PodcastAdapter podcastAdapter;
    private BindingSearchFrag binding;
    private static final String TAG = "SearchFragment";
    private Genre genre;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false);
        initUI();
        return binding.getRoot();
    }
    private void initUI(){
        binding.recSearch.setLayoutManager( new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        podcastAdapter=new PodcastAdapter(this);
        binding.recSearch.setAdapter(podcastAdapter);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewmodel = ViewModelProviders.of(this).get(SearchViewModel.class);
        if(genre!=null){
            viewmodel.searchPodcast(genre.getName());
        }
        viewmodel.getSearched_podcast().observe(this,podcasts -> {
            podcastAdapter.submitList(podcasts);
        });
        viewmodel.getLoading().observe(this,aBoolean -> {
            if(aBoolean!=null){
                if(aBoolean){
                    binding.pbSearch.setVisibility(View.VISIBLE);
                }else {
                    binding.pbSearch.setVisibility(View.GONE);
                }
            }
        });
    }
    public static SearchFragment newInstance(Genre genre) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constant.DATA, genre);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            genre = getArguments().getParcelable(Constant.DATA);
        }
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if(NetworkUtils.getInstance().isNetworkStatusAvailable(getContext())){
                        podcastAdapter.submitList(null);
                        viewmodel.searchPodcast(query);
                    }else {
                        onNetworkStateListener.isNetworkAvailable(false);
                    }
                    searchView.clearFocus();
                    return true;
                }
            };
            searchView.setOnQueryTextFocusChangeListener((view, hasFocus) -> {
                if (hasFocus) {
                    showInputMethod(view.findFocus());
                }
            });
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onPodcastClick(Podcast podcast) {
        if(NetworkUtils.getInstance().isNetworkStatusAvailable(getContext())){
            Intent i=new Intent(getContext(), PodcastActivity.class);
            i.putExtra(Constant.DATA,podcast);
            startActivity(i);
        }else {
            onNetworkStateListener.isNetworkAvailable(false);
        }

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onNetworkStateListener=(OnNetworkStateListener) getActivity();
    }
}
