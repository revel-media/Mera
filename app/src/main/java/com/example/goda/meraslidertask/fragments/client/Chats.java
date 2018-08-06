package com.example.goda.meraslidertask.fragments.client;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.goda.meraslidertask.R;
import com.example.goda.meraslidertask.adapter.OtherRecyclerAdapter;
import com.example.goda.meraslidertask.application.AppConfig;
import com.example.goda.meraslidertask.helper.VolleyCustomRequest;
import com.example.goda.meraslidertask.models.login.Login;
import com.example.goda.meraslidertask.models.user.Conversation;
import com.example.goda.meraslidertask.models.user.UserConversation;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chats extends Fragment {

    String userId;
    int pageId = 1;
    int pageLimit = 1;
    UserConversation conversations;
    LinearLayoutManager layoutManager;
    boolean loadingData = false;
    int pastVisibleItems;

    RecyclerView recyclerView;
    OtherRecyclerAdapter adapter;
    ProgressBar progressBar;

    public Chats() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        userId = getActivity().getSharedPreferences(AppConfig.SHARED_PREFRENCE_NAME, Context.MODE_PRIVATE)
                .getString(AppConfig.SHARED_USER_ID, null);

        recyclerView = view.findViewById(R.id.chatRecView);
        progressBar = view.findViewById(R.id.progressChat);
        layoutManager = new LinearLayoutManager(getActivity());

        if (savedInstanceState != null && savedInstanceState.getSerializable("feed") != null){
            Log.i("onsavestatechat", "onCreateView: ");
            conversations = (UserConversation) savedInstanceState.getSerializable("feed");
            progressBar.setVisibility(View.GONE);
            updateUi();
        } else {
            getDate();
        }

        registerScrollListener();


        return view;
    }


    private void getDate(){
        String url = "http://mera.live/api/app/get_user_conversations?user_id=7"  + "&page_id=" + pageId + "&page_limit=" + pageLimit;

        VolleyCustomRequest request = new VolleyCustomRequest(Request.Method.GET, url, UserConversation.class,
                new Response.Listener<UserConversation>() {
                    @Override
                    public void onResponse(UserConversation response) {
                        loadingData = false;
                        Log.i("chats_response", "onResponse: " + response.getResult().getTotalPages());
                        if (response != null && response.getResult() != null) {
                            conversations = response;
                            progressBar.setVisibility(View.GONE);
                            OtherRecyclerAdapter adapter1 = new OtherRecyclerAdapter(new ArrayList<>(Arrays.asList(response.getResult().getConversations())), getActivity());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter1);

                            adapter1.setCallback(new OtherRecyclerAdapter.OtherCallback() {
                                @Override
                                public void onItemAdded(final int position) {
                                    recyclerView.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyItemInserted(position);
                                        }
                                    });
                                }

                                @Override
                                public void onItemRemoved(final int position) {
                                    recyclerView.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyItemRemoved(position);
                                        }
                                    });
                                }

                                @Override
                                public void onPhoneCall(final String no) {
                                    Dexter.withActivity(getActivity())
                                            .withPermission(Manifest.permission.CALL_PHONE).withListener(new PermissionListener() {
                                        @Override
                                        public void onPermissionGranted(PermissionGrantedResponse response) {
                                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", no, null));
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onPermissionDenied(PermissionDeniedResponse response) {

                                        }

                                        @Override
                                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                                        }
                                    }).check();
                                }
                            });
                        }

//                        if (response != null && response.getResult().getTotalPages() > 0){
//                            if (conversations == null){
//                                conversations = response;
//                                progressBar.setVisibility(View.GONE);
//                                updateUi();
//                            } else {
//                                conversations = response;
//                                progressBar.setVisibility(View.GONE);
//                                updateUi();
//                                Log.i("chat_response_else", "onResponse: ");
//                                Conversation[] tempC = conversations.getResult().getConversations();
//                                Conversation[] newC = response.getResult().getConversations();
//                                ArrayList<Conversation> items = new ArrayList<>(Arrays.asList(tempC));
//                                items.addAll(Arrays.asList(newC));
//                                tempC = new Conversation[items.size()];
//                                int count = 0;
//                                for (Conversation c : items) {
//                                    tempC[count] = c;
//                                    count++;
//                                }
//
//                                conversations.getResult().setConversations(tempC);
//
//                                adapter.notifyDataSetChanged();
//                                recyclerView.scrollToPosition(adapter.removeProgress());
//                            }
//                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingData = false;
                progressBar.setVisibility(View.GONE);
                Log.i("chats_error", "onErrorResponse: " + error.getMessage());
            }
        });

        Volley.newRequestQueue(getActivity()).add(request);
    }

    private void registerScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();


                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        if (!loadingData) {
                            Log.v("SCROLL_DOWN", "Last Item Wow !");
                            loadingData = true;
                            adapter.addProgress();
                            getDate();
                        }
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }
        });
    }

    private void updateUi(){
        if (adapter == null){
            adapter = new OtherRecyclerAdapter(new ArrayList<>(Arrays.asList(conversations.getResult().getConversations())), getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            Log.i("chats_update", "onResponse: "  + conversations.getResult().getConversations().length);

            adapter.setCallback(new OtherRecyclerAdapter.OtherCallback() {
                @Override
                public void onItemAdded(final int position) {
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyItemInserted(position);
                        }
                    });
                }

                @Override
                public void onItemRemoved(final int position) {
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyItemRemoved(position);
                        }
                    });
                }

                @Override
                public void onPhoneCall(final String no) {
                    Dexter.withActivity(getActivity())
                            .withPermission(Manifest.permission.CALL_PHONE).withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", no, null));
                            startActivity(intent);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                        }
                    }).check();
                }
            });
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (conversations != null)
            outState.putSerializable("feed", conversations);
    }
}
