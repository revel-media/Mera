package com.example.goda.meraslidertask.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.goda.meraslidertask.R;
import com.example.goda.meraslidertask.adapter.HomePagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClientHome extends AppCompatActivity {

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.sliding_tabs)TabLayout tabLayout;
    private HomePagerAdapter homePagerAdapter;
    private String [] titles = {"المساعدات", "المحادثات","مفضلتى" , "حسابى"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.HomeActivityLabel);

        homePagerAdapter = new HomePagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(homePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                getSupportActionBar().setTitle(titles[position]);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tab.setIcon(R.drawable.female);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.dark_heart);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.dark_chat);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.users);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tab.setIcon(R.drawable.light_female);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.light_heart);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.light_chat);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.users);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.light_female);
        tabLayout.getTabAt(1).setIcon(R.drawable.light_heart);
        tabLayout.getTabAt(2).setIcon(R.drawable.light_chat);
        tabLayout.getTabAt(3).setIcon(R.drawable.users);
    }


}
