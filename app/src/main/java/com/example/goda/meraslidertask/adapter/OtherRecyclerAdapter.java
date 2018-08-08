package com.example.goda.meraslidertask.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goda.meraslidertask.R;
import com.example.goda.meraslidertask.application.AppConfig;
import com.example.goda.meraslidertask.helper.VolleyCustomRequest;
import com.example.goda.meraslidertask.models.user.Conversation;
import com.example.goda.meraslidertask.models.user.Skill;
import com.example.goda.meraslidertask.models.user.UserConversation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ahmed Ali on 8/6/2018.
 */

public class OtherRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Conversation> conversations;
    private Context context;

    private OtherCallback callback;


    public OtherRecyclerAdapter(ArrayList<Conversation> conversations, Context context) {
        this.conversations = conversations;
        this.context = context;
        Log.i("size_c", "OtherRecyclerAdapter: " + conversations.size());
    }

    public void setCallback(OtherCallback callback){
        this.callback = callback;
    }

    public void addProgress(){
        Conversation conversation = new Conversation();
        conversation.setItemType(Conversation.PROGRESS_TYPE);

        conversations.add(conversation);
        callback.onItemAdded(conversations.size() - 1);
    }

    public int removeProgress(){
        if (conversations != null && conversations.size() > 0) {
            for (Conversation item : conversations) {
                if (item.getItemType() == Conversation.PROGRESS_TYPE) {
                    conversations.remove(conversations.size() - 1);
                    callback.onItemRemoved(conversations.size() - 1);
                    return conversations.size();
                }
            }
        }

        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Conversation.PROGRESS_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.progress_card, parent, false);
            return new ProgressViewHolder(view);
        } else {
            Log.i("create_c", "onCreateViewHolder: " + viewType);
            View view = LayoutInflater.from(context).inflate(R.layout.other_card, parent, false);
            return new OtherViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (conversations.get(position).getItemType() != Conversation.PROGRESS_TYPE){
            OtherViewHolder pHolder = (OtherViewHolder) holder;
            pHolder.bind(conversations.get(position));
            Log.i("bind_c", "onBindViewHolder: " + position);
        }

    }

    @Override
    public int getItemCount() {
        Log.i("size_c", "getItemCount: " + conversations.size());
        return conversations.size();
    }

    @Override
    public int getItemViewType(int position) {
        Log.i("type_c", "getItemViewType: " + conversations.get(position).getItemType());
        if (conversations.get(position).getItemType() == Conversation.PROGRESS_TYPE){
            return Conversation.PROGRESS_TYPE;
        } else {
            return Conversation.CARD_TYPE;
        }
    }

    public class OtherViewHolder extends RecyclerView.ViewHolder {

        CircleImageView pp;
        TextView username;
        TextView skills;
        TextView address;
        RelativeLayout call;
        ImageView fav;

        public OtherViewHolder(View itemView) {
            super(itemView);

            pp = itemView.findViewById(R.id.pp);
            username = itemView.findViewById(R.id.username);
            skills = itemView.findViewById(R.id.skills);
            address = itemView.findViewById(R.id.address);
            call = itemView.findViewById(R.id.call);
            fav = itemView.findViewById(R.id.favIcon);
        }

        public void bind(final Conversation conversation){
            if (conversation.getAddress() != null){
//                address.setText(conversation.getAddress());
            }

            if (conversation.getUsername() != null){
                username.setText(conversation.getUsername());
            }

            if (conversation.getPicture() != null){
                Log.i("picture", "bind: " + conversation.getPicture());
                Picasso.with(context).load(conversation.getPicture()).into(pp);
            }

            if (conversation.getSkills() != null && conversation.getSkills().length > 0){
                String str = "";
                for (Skill s:conversation.getSkills()) {
                    str.concat(s.getName() + ",\n");
                }

                skills.setText(str);
            }

            if (conversation.getPhone() != null){
                call.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        callback.onPhoneCall(conversation.getPhone());
                    }
                });
            }

            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addFav(conversation.getUserId());
                }
            });
        }

        private void addFav(final String id){
            String url = "http://mera.live/api/app/add_user_to_favorite";

            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("fav_response", "onErrorResponse: " + response);
                            try {
                                JSONObject object = new JSONObject(response);
                                if (object.getInt("result") == 1){
                                    Log.i("fav_response_added", "onErrorResponse: ");
                                    fav.setBackground(context.getResources().getDrawable(R.drawable.dark_heart));
                                } else {
                                    Toast.makeText(context, object.getInt("result"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.i("fav_error", "onErrorResponse: " + error.getMessage());
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    String userId = context.getSharedPreferences(AppConfig.SHARED_PREFRENCE_NAME, Context.MODE_PRIVATE)
                            .getString(AppConfig.SHARED_USER_ID, null);
                    map.put("user_id", userId);
                    map.put("favorite_user_id", id);
                    return map;
                }
            };

            Volley.newRequestQueue(context).add(request);
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {

        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OtherCallback {
        void onItemAdded(int position);
        void onItemRemoved(int position);
        void onPhoneCall(String no);
    }

}
