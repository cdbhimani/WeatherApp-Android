package com.weatherapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Constants {
    public static final String PREFS_NAME = "MyPrefs";
    public static final String BASE_URL = "http://api.openweathermap.org/";
    public static final String MAP_BASE_URL = "https://maps.googleapis.com/";
    public static final String IMAGE_URL = "http://openweathermap.org/img/w/";
    public static final String API_KEY = "c6e381d8c7ff98f0fee43775817cf6ad";
    public static final String UNITS = "metric";
    public static String DATE_FORMATE_BG_DATE_NEW = "EEE MMM dd HH:mm";

    public static String WEATHER_UNIT_CELSIUS_KEY = "WEATHER_UNIT_CELSIUS_KEY";

    public static String SPEED_UNIT_KM_KEY = "SPEED_UNIT_KM_KEY";

    public static String PRESSURE_UNIT_INCHES_KEY = "PRESSURE_UNIT_INCHES_KEY";

    public static void setSharedPreString(Context context, String key, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(key, text);
        editor.commit();
    }

    public static String getSharedPreString(Context context, String key) {
        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(key, null);
        return text;
    }

    public static void setSharedPreBoolean(Context context, String key, boolean text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putBoolean(key, text);
        editor.commit();
    }

    public static Boolean getSharedPreBoolean(Context context, String key) {
        SharedPreferences settings;
        Boolean text;
        settings = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getBoolean(key, false);
        return text;
    }

    public static void setSharedPreInt(Context context, String key, int text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putInt(key, text);
        editor.commit();
    }

    public static int getSharedPreInt(Context context, String key) {
        SharedPreferences settings;
        int text;
        settings = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getInt(key, 0);
        return text;
    }

    public static void clearSharedPre(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    public static void removeSharedPre(Context context, String key) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.remove(key);
        editor.commit();
    }
}
