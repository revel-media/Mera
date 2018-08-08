package com.example.goda.meraslidertask.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goda.meraslidertask.R;
import com.example.goda.meraslidertask.adapter.HomePagerAdapter;
import com.example.goda.meraslidertask.utils.MapsService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClientHome extends AppCompatActivity implements View.OnClickListener{

//    @BindView(R.id.toolbar)
//    Toolbar toolbar;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;

    @BindView(R.id.titleText)
    TextView title;

    @BindView(R.id.searchIcon)
    ImageView search;

    @BindView(R.id.mapIcon)
    ImageView map;

    @BindView(R.id.filterIcon)
    ImageView filter;


    private HomePagerAdapter homePagerAdapter;
    private String [] titles = {"المساعدات", "المحادثات","مفضلتى" , "حسابى"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        map.setOnClickListener(this);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setTitle(R.string.HomeActivityLabel);
        //getSupportActionBar().setCustomView(R.layout.assist_toolbar);

        // start MapsService
        Intent intent = new Intent(this,MapsService.class);
        startService(intent);

        homePagerAdapter = new HomePagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(homePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //getSupportActionBar().setTitle(titles[position]);

//                if (position < 2){
//                    configActionBar(true, titles[position]);
//                    configActionBar(true, titles[position]);
//                }
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
                        configActionBar(true, titles[tab.getPosition()]);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.dark_chat);
                        configActionBar(false, titles[tab.getPosition()]);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.dark_heart);
                        configActionBar(true, titles[tab.getPosition()]);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.users);
                        configActionBar(false, titles[tab.getPosition()]);
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
                        tab.setIcon(R.drawable.light_chat);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.light_heart);
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

    private void configActionBar(Boolean show, String titleStr){
        //View header = getSupportActionBar().getCustomView();
//        TextView title = header.findViewById(R.id.titleText);
//        ImageView search = header.findViewById(R.id.searchIcon);
//        ImageView map = header.findViewById(R.id.mapIcon);
//        ImageView filter = header.findViewById(R.id.filterIcon);

        title.setText(titleStr);

        if (show){
            search.setVisibility(View.VISIBLE);
            map.setVisibility(View.VISIBLE);
            filter.setVisibility(View.VISIBLE);
        } else {
            search.setVisibility(View.GONE);
            map.setVisibility(View.GONE);
            filter.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ClientHome.this, MapsActivity.class);
        startActivity(intent);
    }
}
