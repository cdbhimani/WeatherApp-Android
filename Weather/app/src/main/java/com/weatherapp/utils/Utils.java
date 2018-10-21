package com.weatherapp.utils;

import android.content.Context;

import com.weatherapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.weatherapp.utils.Constants.getSharedPreBoolean;

public class Utils {
    public static String convertWeatherUnit(Context context, String farenhit){
        String str = null;
        try {
            if(getSharedPreBoolean(context, Constants.WEATHER_UNIT_CELSIUS_KEY)){
                str = String.valueOf(Math.round(Double.parseDouble(farenhit) - 32) * 5/9) + context.getResources().getString(R.string.celsius_symbol);
            }else{
                str = String.valueOf(Math.round(Double.parseDouble(farenhit))) + context.getResources().getString(R.string.celsius_symbol);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return str ;
    }

    public static String convertSpeed(Context context, String miles){
        String str = null;
        try {
            if(getSharedPreBoolean(context, Constants.SPEED_UNIT_KM_KEY)){
                str = String.valueOf(Math.round(Double.parseDouble(miles) / 0.62137));
            }else{
                str = String.valueOf(Math.round(Double.parseDouble(miles)));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return str ;
    }

    public static String convertSpeedUnit(Context context){
        String str = null;
        try {
            if(getSharedPreBoolean(context, Constants.SPEED_UNIT_KM_KEY)){
                str = context.getResources().getString(R.string.speed_unit_kmph);
            }else{
                str = context.getResources().getString(R.string.speed_unit_mph);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return str ;
    }

    public static String convertPressure(Context context, String millibar){
        String str = null;
        try {
            if(getSharedPreBoolean(context, Constants.PRESSURE_UNIT_INCHES_KEY)){
                str = String.valueOf(Math.round(Double.parseDouble(millibar) * 0.0295301));
            }else{
                str = String.valueOf(Math.round(Double.parseDouble(millibar)));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return str ;
    }

    public static String convertPressureUnit(Context context){
        String str = null;
        try {
            if(getSharedPreBoolean(context, Constants.PRESSURE_UNIT_INCHES_KEY)){
                str = context.getResources().getString(R.string.pressure_unit_inn);
            }else{
                str = context.getResources().getString(R.string.pressure_unit_mbb);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return str ;
    }
    public static int getIcon60(String iconhint) {
        int icon = 0;
        if (iconhint.equalsIgnoreCase("01d")) {
            icon = R.drawable.clear_01d_icon_60;
        } else if (iconhint.equalsIgnoreCase("01n")) {
            icon = R.drawable.clear_01n_icon_60;
        } else if (iconhint.equalsIgnoreCase("02d")) {
            icon = R.drawable.few_clouds_02d_icon_60;
        } else if (iconhint.equalsIgnoreCase("02n")) {
            icon = R.drawable.few_clouds_02n_icon_60;
        } else if (iconhint.equalsIgnoreCase("03d")) {
            icon = R.drawable.m_clouds_03d_03n_icon_60;
        } else if (iconhint.equalsIgnoreCase("03n")) {
            icon = R.drawable.m_clouds_03d_03n_icon_60;
        } else if (iconhint.equalsIgnoreCase("04d")) {
            icon = R.drawable.clouds_04d_04n_icon_60;
        } else if (iconhint.equalsIgnoreCase("04n")) {
            icon = R.drawable.clouds_04d_04n_icon_60;
        } else if (iconhint.equalsIgnoreCase("09d")) {
            icon = R.drawable.shwr_09d_09n_icon_60;
        } else if (iconhint.equalsIgnoreCase("09n")) {
            icon = R.drawable.shwr_09d_09n_icon_60;
        } else if (iconhint.equalsIgnoreCase("10d")) {
            icon = R.drawable.rain_10d_icon_60;
        } else if (iconhint.equalsIgnoreCase("10n")) {
            icon = R.drawable.rain_10n_icon_60;
        } else if (iconhint.equalsIgnoreCase("11d")) {
            icon = R.drawable.ec_11d_11n_icon_60;
        } else if (iconhint.equalsIgnoreCase("11n")) {
            icon = R.drawable.ec_11d_11n_icon_60;
        } else if (iconhint.equalsIgnoreCase("13d")) {
            icon = R.drawable.snow_13d_13n_icon_60;
        } else if (iconhint.equalsIgnoreCase("13n")) {
            icon = R.drawable.snow_13d_13n_icon_60;
        } else if (iconhint.equalsIgnoreCase("50d")) {
            icon = R.drawable.mist_50d_50n_icon;
        } else if (iconhint.equalsIgnoreCase("50n")) {
            icon = R.drawable.mist_50d_50n_icon;
        } else if (iconhint.equalsIgnoreCase("r")) {
            icon = R.drawable.shwr_09d_09n_icon_60;
        } else if (iconhint.equalsIgnoreCase("sn50")) {
            icon = R.drawable.snow_13d_13n_icon_60;
        } else if (iconhint.equalsIgnoreCase("w50")) {
            icon = R.drawable.wind_w50_icon;
        }else{
            icon = R.drawable.clouds_04d_04n_icon_60;
        }
        return icon;
    }
    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds*1000);
        return formatter.format(calendar.getTime());
    }
}
