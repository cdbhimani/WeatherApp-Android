package com.weatherapp.repository;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.weatherapp.db.AppDatabase;
import com.weatherapp.db.entity.CityData;

import java.util.List;


public class LocalDataRepository {

    private static LocalDataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<CityData>> mObservableCities;

    private LocalDataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableCities = new MediatorLiveData<>();

        mObservableCities.addSource(mDatabase.cityDao().loadCities(),
                cityEntities -> {
                    if (mDatabase != null) {
                        mObservableCities.postValue(cityEntities);
                    }
                });
    }

    public static LocalDataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (LocalDataRepository.class) {
                if (sInstance == null) {
                    sInstance = new LocalDataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<CityData>> getProducts() {
        return mObservableCities;
    }
    public LiveData<List<CityData>> getCities() {
        return mObservableCities;
    }

}
