package com.example.goda.meraslidertask.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;

import java.util.Locale;


/**
 * Created by Ahmed Ali on 8/6/2018.
 */

public class MeraApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        setDefaultLanguage(getApplicationContext(), "");
    }

    public static void setDefaultLanguage(Context context, String lang) {
        Locale locale = new Locale("ar");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }
}
