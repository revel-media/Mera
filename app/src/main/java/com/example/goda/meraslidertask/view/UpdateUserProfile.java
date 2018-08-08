package com.example.goda.meraslidertask.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goda.meraslidertask.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateUserProfile extends AppCompatActivity implements View.OnClickListener{

    ImageView pp_image;

    private final static String API ="http://mera.live/api/user/update_profile";
    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    private Gson gson;
    private Uri ppImageUri;
    private Bitmap ppBitmap;
    private static final int PICK_PP_IMAGE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);

        // Volley Request and Gson
        requestQueue = Volley.newRequestQueue(this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

   private void sendData(){
        stringRequest = new StringRequest(Request.Method.POST, API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(UpdateUserProfile.this, response,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateUserProfile.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put("user_id","");
                params.put("name","");
                params.put("email","");
                params.put("iqama_no","");

                if (ppBitmap != null){
                    String pp = getStringImage(ppBitmap);
                    params.put("picture", pp);
                }else{
                    //current photo
//                    params.put("picture",profilePhoto);
                }


                params.put("profile_pic","");

                return params;
            }
        };
        requestQueue.add(stringRequest);
   }

    @Override
    public void onClick(View v) {

    }

    private void openGalleryForPPImage() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery , PICK_PP_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_PP_IMAGE){
            ppImageUri = data.getData();
            pp_image.setImageURI(ppImageUri);
            Toast.makeText(UpdateUserProfile.this, ppImageUri.toString(), Toast.LENGTH_LONG).show();
            try {
                ppBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),ppImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // convert Bitmap image to String for volley request
    public String getStringImage(Bitmap bitmap){
        Log.i("MyBitmap",""+bitmap);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

}
