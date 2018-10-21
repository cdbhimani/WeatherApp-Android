

package com.weatherapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.weatherapp.WeatherApplication;
import com.weatherapp.db.entity.CityData;

import java.util.List;


public class CityListViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<CityData>> mObservableCities;

    public CityListViewModel(Application application) {
        super(application);

        mObservableCities = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableCities.setValue(null);

        LiveData<List<CityData>> cities = ((WeatherApplication) application).getRepository()
                .getCities();

        // observe the changes of the products from the database and forward them
        mObservableCities.addSource(cities, mObservableCities::setValue);
    }

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<CityData>> getCities() {
        return mObservableCities;
    }
}
