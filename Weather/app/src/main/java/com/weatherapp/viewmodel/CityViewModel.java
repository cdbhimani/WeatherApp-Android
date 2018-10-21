package com.weatherapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.weatherapp.models.WeatherModel;
import com.weatherapp.repository.RemoteDataRepository;


public class CityViewModel extends AndroidViewModel {

    private final MediatorLiveData<WeatherModel> mObservableWeather;

    public CityViewModel(Application application, double lat, double lon) {
        super(application);

        mObservableWeather = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableWeather.setValue(null);



        LiveData<WeatherModel> cities = RemoteDataRepository.getRepoInstance().getWeatherDataByLatlong(lat,lon,mObservableWeather);

        mObservableWeather.addSource(cities, mObservableWeather::setValue);
    }

    public LiveData<WeatherModel> getWeatherByLatLong() {
        return mObservableWeather;
    }

}
