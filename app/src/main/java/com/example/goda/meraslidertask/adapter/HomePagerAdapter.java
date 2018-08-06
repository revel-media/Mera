package com.example.goda.meraslidertask.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.goda.meraslidertask.R;
import com.example.goda.meraslidertask.fragments.client.Assistance;
import com.example.goda.meraslidertask.fragments.client.Chats;
import com.example.goda.meraslidertask.fragments.client.Favourites;
import com.example.goda.meraslidertask.fragments.client.MyProfile;

public class HomePagerAdapter extends FragmentPagerAdapter {

    private Context context ;

    public HomePagerAdapter(Context context, android.support.v4.app.FragmentManager fm) {
        super(fm);
        this.context = context;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new Assistance();
        }else if (position == 1){
            return new Chats();
        }else if (position == 2){
            return new Favourites();
        }else{
            return new MyProfile();
        }
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return context.getString(R.string.AssistanceLabel);
            case 1:
                return context.getString(R.string.ChatsLabel);
            case 2:
                return context.getString(R.string.FavouritesLabel);
            case 3:
                return context.getString(R.string.MyProfileLabel);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}
