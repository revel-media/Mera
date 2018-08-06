package com.example.goda.meraslidertask.fragments.client;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.goda.meraslidertask.R;
import com.example.goda.meraslidertask.application.AppConfig;
import com.example.goda.meraslidertask.helper.VolleyCustomRequest;
import com.example.goda.meraslidertask.models.login.Login;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyProfile extends Fragment implements View.OnClickListener {

    FrameLayout topLay;
    LinearLayout dataLayout;
    CircleImageView pp;
    TextView username;
    TextView email;
    TextView mobile;
    ImageView toggleInfo;
    ExpandableRelativeLayout infoExpand;
    TextView fullName;
    TextView fullEmail;
    TextView fullmobile;
    ImageView toggleAddress;
    ExpandableRelativeLayout addressExpand;
    TextView address;
    TextView city;
    TextView buildingNo;
    TextView infoT;
    TextView addressT;

    private String userId;
    private Drawable initialArrow;

    public MyProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        topLay =  view.findViewById(R.id.topLayout);
        dataLayout =  view.findViewById(R.id.dataLayout);
        pp =  view.findViewById(R.id.pp);
        username =  view.findViewById(R.id.username);
        email =  view.findViewById(R.id.email);
        mobile =  view.findViewById(R.id.mobNum);
        infoExpand = view.findViewById(R.id.userInfo);
        toggleInfo = view.findViewById(R.id.toggleInfo);
        fullName = view.findViewById(R.id.fullname);
        fullEmail = view.findViewById(R.id.fullEmail);
        fullmobile = view.findViewById(R.id.fullMobile);
        toggleAddress = view.findViewById(R.id.toggleAddress);
        addressExpand = view.findViewById(R.id.userAddress);
        address = view.findViewById(R.id.address);
        city = view.findViewById(R.id.city);
        buildingNo = view.findViewById(R.id.buildingNum);
        infoT = view.findViewById(R.id.userInfoText);
        addressT = view.findViewById(R.id.userAddressText);


        initialArrow = toggleInfo.getDrawable();

        infoExpand.collapse();
        infoExpand.setExpanded(false);
        addressExpand.collapse();
        addressExpand.setExpanded(false);

        toggleInfo.setOnClickListener(this);
        toggleAddress.setOnClickListener(this);

        infoT.setOnClickListener(this);
        addressT.setOnClickListener(this);

        userId = getActivity().getSharedPreferences(AppConfig.SHARED_PREFRENCE_NAME, Context.MODE_PRIVATE)
                .getString(AppConfig.SHARED_USER_ID, null);preformSubmit();

        preformSubmit();
        return view;
    }


    private void preformSubmit(){
        Log.i("info_response", "onResponse: " + userId);
        String url = "http://mera.live/api/user/get_user?user_id=" + userId;

        VolleyCustomRequest request = new VolleyCustomRequest(Request.Method.GET, url, Login.class,
                new Response.Listener<Login>() {
                    @Override
                    public void onResponse(Login response) {
                        Log.i("info_response", "onResponse: ");
                        if (response != null && response.getResult().getUserID() != null){
                            topLay.setVisibility(View.GONE);
                            dataLayout.setVisibility(View.VISIBLE);

                            if (response.getResult().getFullname() != null){
                                username.setText(response.getResult().getFullname());
                                fullEmail.setText(response.getResult().getFullname());
                            }

                            if (response.getResult().getAddress() != null){
                                address.setText(response.getResult().getAddress());
                            }

                            if (response.getResult().getCity() != null){
                                city.setText(response.getResult().getCity());
                            }

                            if (response.getResult().getBuildingInfo() != null){
                                buildingNo.setText(response.getResult().getBuildingInfo());
                            }

                            if (response.getResult().getEmail() != null){
                                email.setText(response.getResult().getEmail());
                                fullEmail.setText(response.getResult().getEmail());
                            }

                            if (response.getResult().getPhone() != null){
                                mobile.setText(response.getResult().getPhone());
                                fullmobile.setText(response.getResult().getPhone());
                            }

                            if (response.getResult().getPicture() != null){
                                Picasso.with(getActivity()).load(response.getResult().getPicture()).into(pp);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("info_error", "onErrorResponse: " + error.getMessage());
            }
        });

        Volley.newRequestQueue(getActivity()).add(request);
    }

    @Override
    public void onClick(View v) {
        Animation startRotateAnimation;
        switch (v.getId()){
            case R.id.userInfoText:
                infoExpand.toggle(300, new AccelerateDecelerateInterpolator());
                break;
            case R.id.userAddressText:
                addressExpand.toggle(300, new AccelerateDecelerateInterpolator());
                break;
            case R.id.toggleInfo:
                infoExpand.toggle(300, new AccelerateDecelerateInterpolator());
                if (infoExpand.isExpanded()){
                    startRotateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_plus_90);
                } else {
                    startRotateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_minus_90);
                }

                v.startAnimation(startRotateAnimation);
                startRotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        toggleInfo.clearAnimation();
                        if (infoExpand.isExpanded()){
                            toggleInfo.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));
                        } else {
                            toggleInfo.setBackground(initialArrow);
                        }


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                break;
            case R.id.toggleAddress:
                addressExpand.toggle(300, new AccelerateDecelerateInterpolator());
                if (addressExpand.isExpanded()){
                    rotate(toggleAddress, false);
                } else {
                    rotate(toggleAddress, true);
                }
                break;
        }
    }

    private void rotate(final View v, final boolean down){
        Animation startRotateAnimation;
        if (down){
            startRotateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_minus_90);
        } else {
            startRotateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_plus_90);
        }

        v.startAnimation(startRotateAnimation);
        startRotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.clearAnimation();
                if (down){
                    v.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));
                } else {
                    v.setBackground(initialArrow);
                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
