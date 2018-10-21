package com.weatherapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.weatherapp.models.MapRequestResponse;
import com.weatherapp.repository.RemoteDataRepository;


public class CityNameViewModel extends AndroidViewModel {

    private final MediatorLiveData<MapRequestResponse> mObservableForecast;
    public CityNameViewModel(Application application, double lat, double lon) {
        super(application);

        mObservableForecast = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableForecast.setValue(null);


        String s = String.valueOf(lat) + "," + String.valueOf(lon);

        LiveData<MapRequestResponse> cities = RemoteDataRepository.getRepoInstance().getCitynameFromLatLong(s);

        mObservableForecast.addSource(cities, mObservableForecast::setValue);
    }

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<MapRequestResponse> getCityName() {
        return mObservableForecast;
    }


}
