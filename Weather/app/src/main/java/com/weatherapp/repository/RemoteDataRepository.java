package com.weatherapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.weatherapp.WeatherApplication;
import com.weatherapp.api.WeatherService;
import com.weatherapp.models.ForecastModel;
import com.weatherapp.models.MapRequestResponse;
import com.weatherapp.models.WeatherModel;
import com.weatherapp.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RemoteDataRepository {

    private static WeatherService weatherService;
    private static RemoteDataRepository instance;
    private static WeatherService weatherMapService;

    public static WeatherService getInstance() {
        weatherService = WeatherApplication.initRestClient();
        return weatherService;
    }

    public static RemoteDataRepository getRepoInstance() {
        instance = new RemoteDataRepository();
        return instance;
    }

    public static WeatherService getInstanceMap() {
        weatherMapService = WeatherApplication.initRestClient1();
        return weatherMapService;
    }

    public LiveData<MapRequestResponse> getCitynameFromLatLong(String latlong) {
        final MutableLiveData<MapRequestResponse> data = new MutableLiveData<>();

        WeatherApplication.initRestClient().getMapData(latlong).enqueue(new Callback<MapRequestResponse>() {
            @Override
            public void onResponse(@NonNull Call<MapRequestResponse> call, @NonNull Response<MapRequestResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<MapRequestResponse> call, @NonNull Throwable t) {

            }
        });

        return data;
    }


    public LiveData<WeatherModel> getWeatherDataByLatlong(double lat, double lon, MediatorLiveData<WeatherModel> mObservableWeather) {
        final MutableLiveData<WeatherModel> data = new MutableLiveData<>();

        WeatherApplication.initRestClient().getWeatherDataByLatLon(lat, lon, Constants.API_KEY, Constants.UNITS).enqueue(new Callback<WeatherModel>() {

            @Override
            public void onResponse(@NonNull Call<WeatherModel> call, @NonNull Response<WeatherModel> response) {
                data.setValue(response.body());
                mObservableWeather.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<WeatherModel> call, @NonNull Throwable t) {

            }
        });

        return data;
    }

    public LiveData<ForecastModel> getWeatherForecastLatlong(double lat, double lon, MediatorLiveData<ForecastModel> mObservableForecast) {
        final MutableLiveData<ForecastModel> data = new MutableLiveData<>();

        WeatherApplication.initRestClient().getWeatherForecast(lat, lon, Constants.API_KEY, Constants.UNITS).enqueue(new Callback<ForecastModel>() {

            @Override
            public void onResponse(@NonNull Call<ForecastModel> call, @NonNull Response<ForecastModel> response) {
                data.setValue(response.body());
                mObservableForecast.setValue(response.body());

            }

            @Override
            public void onFailure(@NonNull Call<ForecastModel> call, @NonNull Throwable t) {

            }
        });

        return data;
    }
}
