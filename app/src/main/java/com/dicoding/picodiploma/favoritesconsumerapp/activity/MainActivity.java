package com.dicoding.picodiploma.favoritesconsumerapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.dicoding.picodiploma.favoritesconsumerapp.R;
import com.dicoding.picodiploma.favoritesconsumerapp.ui.main.SectionsPagerAdapter;
import com.dicoding.picodiploma.favoritesconsumerapp.utils.LoadCallback;
import com.google.android.material.tabs.TabLayout;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoadCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.dicoding_favorite));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_setting) {
            Intent localeIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(localeIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void postExecute(Cursor cursor) {

    }
}