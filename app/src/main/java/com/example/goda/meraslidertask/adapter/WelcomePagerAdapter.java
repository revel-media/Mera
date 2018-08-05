package com.example.goda.meraslidertask.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.goda.meraslidertask.R;

/**
 * Created by Goda on 09/07/2018.
 */

public class WelcomePagerAdapter extends PagerAdapter {

    private int[] images;
    private LayoutInflater layoutInflater;
    private Context context;

    public WelcomePagerAdapter(int [] images, Context context){
        this.images = images;
        this.context= context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.swiplayout,container, false);
        ImageView imageView = view.findViewById(R.id.sliderImage);
        ImageView indicator1 = view.findViewById(R.id.indicator1);
        ImageView indicator2 = view.findViewById(R.id.indicator2);
        ImageView indicator3 = view.findViewById(R.id.indicator3);

        if (position ==0){
            indicator1.setImageResource(R.drawable.active_dot);
        }else  if (position ==1){
            indicator2.setImageResource(R.drawable.active_dot);
        }else  if (position ==2){
            indicator3.setImageResource(R.drawable.active_dot);
        }

        imageView.setImageResource(images[position]);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
