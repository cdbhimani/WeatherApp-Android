package com.weatherapp.viewmodel.modelFactory;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.weatherapp.viewmodel.CityViewModel;

public class CityWeatherDetailModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;
    private double lat;
    private double lon;


    public CityWeatherDetailModelFactory(Application application, double lat, double lon) {
        mApplication = application;
        this.lat = lat;
        this.lon = lon;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new CityViewModel(mApplication, lat,lon);
    }
}