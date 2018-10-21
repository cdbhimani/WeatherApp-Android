package com.weatherapp.ui.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.weatherapp.R;
import com.weatherapp.models.MapRequestResponse;
import com.weatherapp.base.BaseActivity;
import com.weatherapp.db.AppDatabase;
import com.weatherapp.db.entity.CityData;
import com.weatherapp.ui.fragments.CityForecastFragment;
import com.weatherapp.ui.fragments.CityFragment;
import com.weatherapp.ui.fragments.CityListFragment;
import com.weatherapp.ui.fragments.HelpFragment;
import com.weatherapp.ui.fragments.SettingsFragment;
import com.weatherapp.viewmodel.CityNameViewModel;
import com.weatherapp.viewmodel.modelFactory.CityNameViewModelFactory;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    int PLACE_PICKER_REQUEST = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            CityListFragment fragment = new CityListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, CityListFragment.TAG).commit();
        }



    }

    public void show(CityData city) {

        CityFragment productFragment = CityFragment.forProduct(city.getLatitude(), city.getLongitude());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("city")
                .replace(R.id.fragment_container,
                        productFragment, null).commit();
    }

        public void showForecast(CityData city) {

        CityForecastFragment productFragment = CityForecastFragment.forProduct(city.getLatitude(),city.getLongitude());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("cityforecast")
                .replace(R.id.fragment_container,
                        productFragment, null).commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_add) {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            try {
                startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
            return true;
        }
        else if (id == R.id.action_help) {
            HelpFragment helpFragment = new HelpFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("help")
                    .replace(R.id.fragment_container,
                            helpFragment, null).commit();
            return true;
        }
        else if (id == R.id.action_settings) {
            SettingsFragment settingsFragment = new SettingsFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("settings")
                    .replace(R.id.fragment_container,
                            settingsFragment, null).commit();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                double mPostLat = data.getDoubleExtra("latitude", 0.0);
//                double mPostLong = data.getDoubleExtra("longitude", 0.0);
//               String add= data.getStringExtra("address");
//
//            }
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());

                try {
                    List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);
                    String address = addresses.get(0).getAddressLine(0);
                    String city = addresses.get(0).getLocality();
                    //String country = addresses.get(0).getAddressLine(2);
                    String toastMsg = String.format("Place: %s", place.getName() + "City:" + city);
                    Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                    CityData cdata = new CityData();
                    cdata.setLatitude(place.getLatLng().latitude);
                    cdata.setLongitude(place.getLatLng().longitude);
                    cdata.setName(city);

                    Completable.fromAction(() -> AppDatabase.getAppDatabase(MainActivity.this).cityDao()
                            .insertCity(cdata))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onComplete() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            });
                } catch (IOException e) {

                    e.printStackTrace();
                    CityNameViewModel viewModel = ViewModelProviders.of(this, new CityNameViewModelFactory(this.getApplication(), place.getLatLng().latitude, place.getLatLng().longitude)).get(CityNameViewModel.class);
                    viewModel.getCityName().observe(this, new Observer<MapRequestResponse>() {
                        @Override
                        public void onChanged(@Nullable MapRequestResponse response) {
                            if (response != null) {
                                String city = response.getResults().get(0).getFormatted_address();
                                CityData cdata = new CityData();
                                cdata.setLatitude(place.getLatLng().latitude);
                                cdata.setLongitude(place.getLatLng().longitude);
                                cdata.setName(city);

                                Completable.fromAction(() -> AppDatabase.getAppDatabase(MainActivity.this).cityDao()
                                        .insertCity(cdata))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new CompletableObserver() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onComplete() {

                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }
                                        });
                            }
                        }
                    });
                }

            }

        }
    }
}
