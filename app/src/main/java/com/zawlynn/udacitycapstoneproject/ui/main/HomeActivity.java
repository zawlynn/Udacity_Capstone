package com.zawlynn.udacitycapstoneproject.ui.main;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.databinding.BindingHome;
import com.zawlynn.udacitycapstoneproject.ui.main.event.OnNetworkStateListener;
import com.zawlynn.udacitycapstoneproject.ui.main.fragment.FavouriteFragment;
import com.zawlynn.udacitycapstoneproject.ui.main.fragment.HomeFragment;
import com.zawlynn.udacitycapstoneproject.ui.main.fragment.LibraryFragment;
import com.zawlynn.udacitycapstoneproject.ui.main.fragment.SearchFragment;
import java.util.ArrayList;
import java.util.List;
public class HomeActivity extends AppCompatActivity implements OnNetworkStateListener {
    BindingHome binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_home);
        setSupportActionBar(binding.toolbar);
        setupViewPager(binding.viewpager);
        binding.tabs.setupWithViewPager(binding.viewpager);
        binding.tabs.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(binding.viewpager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        int tabIconColor = ContextCompat.getColor(HomeActivity.this, R.color.colorAccent);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        int tabIconColor = ContextCompat.getColor(HomeActivity.this, R.color.gray);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );
        setupTabIcons();
    }
    private void setupTabIcons() {
        binding.tabs.getTabAt(0).setIcon(R.mipmap.ic_discover);
        binding.tabs.getTabAt(1).setIcon(R.mipmap.ic_search);
        binding.tabs.getTabAt(2).setIcon(R.mipmap.ic_fav);
        binding.tabs.getTabAt(3).setIcon(R.mipmap.ic_library);
        int tabIconColor = ContextCompat.getColor(HomeActivity.this, R.color.colorAccent);
        binding.tabs.getTabAt(0).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeFragment(), "HOME");
        adapter.addFrag(new SearchFragment(), "SEARCH");
        adapter.addFrag(new FavouriteFragment(), "FAVOURITE");
        adapter.addFrag(new LibraryFragment(), "LIBRARY");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void isNetworkAvailable(boolean staus) {
        if(!staus){
            Snackbar.make(binding.container,getResources().getString(R.string.no_internet),
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
