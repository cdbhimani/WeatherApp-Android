package com.weatherapp.api;


import com.weatherapp.models.ForecastModel;
import com.weatherapp.models.MapRequestResponse;
import com.weatherapp.models.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("/maps/api/geocode/json")
    Call<MapRequestResponse> getMapData(@Query("latlng") String latlng);


    @GET("/data/2.5/weather")
    Call<WeatherModel> getWeatherDataByLatLon(@Query("lat") double lat, @Query("lon") double lon, @Query("appid") String appId, @Query("units") String units);

    @GET("/data/2.5/forecast")
    Call<ForecastModel> getWeatherForecast(@Query("lat") double lat, @Query("lon") double lon, @Query("appid") String appId, @Query("units") String units);

}