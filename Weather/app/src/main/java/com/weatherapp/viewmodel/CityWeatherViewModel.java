package com.weatherapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.weatherapp.models.ForecastModel;
import com.weatherapp.repository.RemoteDataRepository;


public class CityWeatherViewModel extends AndroidViewModel {

    private final MediatorLiveData<ForecastModel> mObservableForecast;
    public CityWeatherViewModel(Application application,double lat, double lon) {
        super(application);

        mObservableForecast = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableForecast.setValue(null);



        LiveData<ForecastModel> cities = RemoteDataRepository.getRepoInstance().getWeatherForecastLatlong(lat,lon,mObservableForecast);

        mObservableForecast.addSource(cities, mObservableForecast::setValue);
    }

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<ForecastModel> getForecast() {
        return mObservableForecast;
    }


}
