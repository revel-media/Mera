package com.example.goda.meraslidertask.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Goda on 09/07/2018.
 */

public class PreferencesUtils {

    public static void saveEmail (String email, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_EMAIL, email);
        editor.apply();

    }

    public static void saveName (String name, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_NAME, name);
        editor.apply();

    }
    public static void savePhone (String phone, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_PHONE, phone);
        editor.apply();

    }


    public static void saveAddress (String address, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_ADDRESS, address);
        editor.apply();

    }

    public static void saveBuildingNumber (String buildingNumber, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_BUILDING_NUMBER, buildingNumber);
        editor.apply();

    }

    public static void saveCity (String city, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_CITY, city);
        editor.apply();

    }

    public static void saveFloorNumber (String floorNumber, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_FLOOR_NUMBER, floorNumber);
        editor.apply();

    }

    public static void saveNeighborhood (String neighborhood, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_NEIGHBORHOOD, neighborhood);
        editor.apply();

    }

    public static void saveId (String id, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_ID, id);
        editor.apply();

    }

    public static void saveAccountType (String accountType, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_ACCOUNT_TYPE, accountType);
        editor.apply();

    }

    public static void saveLat (String latitude, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_LAT, latitude);
        editor.apply();

    }

    public static void saveLng (String longitude, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_LNG, longitude);
        editor.apply();

    }

    public static String getLat (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_LAT, null);
    }

    public static String getLng (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_LNG, null);
    }


    public static String getEmail (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_EMAIL, null);
    }

    public static String getAddress (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_ADDRESS, null);
    }

    public static String getPassword (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_PASSWORD, null);
    }

    public static String getName (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_NAME, null);
    }

    public static String getid (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_ID, null);
    }

    public static String getPhone (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_PHONE, null);
    }

    public static String getAccountType (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_ACCOUNT_TYPE, null);
    }

    public static String getCity (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_CITY, null);
    }

    public static String getBuildingNumber (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_BUILDING_NUMBER, null);
    }

    public static String getFloorNumber (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_FLOOR_NUMBER, null);
    }

    public static void  deleteEmail (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().clear().commit();
    }


}
