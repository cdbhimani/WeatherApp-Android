

package com.weatherapp;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weatherapp.api.WeatherService;
import com.weatherapp.db.AppDatabase;
import com.weatherapp.repository.LocalDataRepository;
import com.weatherapp.utils.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Android Application class. Used for accessing singletons.
 */
public class WeatherApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

    }
    public static WeatherService initRestClient() {

        // GSON converter factory for Retrofit response
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …

// add logging as last interceptor
        httpClient.addInterceptor(logging);

        Retrofit adapter = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(httpClient.build())
                .build();
        return adapter.create(WeatherService.class);
    }
    public static WeatherService initRestClient1() {

        // GSON converter factory for Retrofit response
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();


        Retrofit adapter = new Retrofit.Builder().baseUrl(Constants.MAP_BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        return adapter.create(WeatherService.class);
    }
    public AppDatabase getDatabase() {
        return AppDatabase.getAppDatabase(this);
    }

    public LocalDataRepository getRepository() {
        return LocalDataRepository.getInstance(getDatabase());
    }
}
