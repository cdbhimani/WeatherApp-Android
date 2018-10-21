

package com.weatherapp.db.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.weatherapp.db.entity.CityData;

import java.util.List;


@Dao
public interface CityDao {
    @Query("SELECT * FROM cities")
    LiveData<List<CityData>> loadCities();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCity(CityData city);

    @Query("Delete FROM cities")
   void deleteCities();
}
