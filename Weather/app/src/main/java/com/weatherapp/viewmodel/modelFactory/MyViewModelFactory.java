package com.weatherapp.viewmodel.modelFactory;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.weatherapp.viewmodel.CityWeatherViewModel;

public class MyViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;
    private double lat;
    private double lon;


    public MyViewModelFactory(Application application, double lat,double lon) {
        mApplication = application;
        this.lat = lat;
        this.lon = lon;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new CityWeatherViewModel(mApplication, lat,lon);
    }
}