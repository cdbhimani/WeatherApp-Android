

package com.weatherapp.ui.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.weatherapp.R;
import com.weatherapp.models.WeatherModel;
import com.weatherapp.ui.activities.MainActivity;
import com.weatherapp.utils.Constants;
import com.weatherapp.utils.Utils;
import com.weatherapp.viewmodel.CityViewModel;
import com.weatherapp.viewmodel.modelFactory.CityWeatherDetailModelFactory;

public class CityFragment extends Fragment {

    TextView tv_cityname, tv_temp, tv_climate_description, humidity, pressure, wind,tv_date;
    ImageView weather_icon;
    CityViewModel viewModel;
    private String TAG = "CityFragment";
    private static final String LAT = "lat";
    private static final String LON = "lon";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.city_fragment,
                container, false);

        tv_cityname = view.findViewById(R.id.tv_cityname);
        tv_date = view.findViewById(R.id.tv_date);
        tv_temp = view.findViewById(R.id.tv_temp);
        tv_climate_description = view.findViewById(R.id.tv_climate_description);
        weather_icon = view.findViewById(R.id.weather_icon);
        humidity = view.findViewById(R.id.hum);
        pressure = view.findViewById(R.id.pressure);
        wind = view.findViewById(R.id.wind);

        return view;

    }
    public void loadCityData() {

        viewModel.getWeatherByLatLong().observe(this, new Observer<WeatherModel>() {
            @Override
            public void onChanged(@Nullable WeatherModel data) {
                if (data != null) {
                    generateUI(data);

                } else {
                   // mBinding.setIsLoading(true);
                }
                // espresso does not know how to wait for data binding's loop so we execute changes
                // sync.
               // mBinding.executePendingBindings();
            }


        });

    }

    @SuppressLint("SetTextI18n")
    private void generateUI(WeatherModel weatherModel) {


        tv_temp.setText(Utils.convertWeatherUnit(getActivity(), String.valueOf(weatherModel.getMain().getTemp())) + "");
        tv_climate_description.setText(weatherModel.getWeather()[0].getMain() + ", " + weatherModel.getWeather()[0].getDescription());
        loadImageFromUrlToImageView(weather_icon, weatherModel.getWeather()[0].getIcon());
        String wind_str = getActivity().getResources().getString(R.string.wind_text) + " " + Utils.convertSpeed(getActivity(), String.valueOf(weatherModel.getWind().getSpeed())) + " " + Utils.convertSpeedUnit(getActivity());
        String humidity_str = getActivity().getResources().getString(R.string.humidity_text) + "\n" + weatherModel.getMain().getHumidity() + getActivity().getResources().getString(R.string.humidity_symbol);
        String pressure_str = getActivity().getResources().getString(R.string.pressure_text) + " " + Utils.convertPressure(getActivity(), String.valueOf(weatherModel.getMain().getPressure())) + " " + Utils.convertPressureUnit(getActivity());
        wind.setText(wind_str);
        tv_cityname.setText(weatherModel.getName()+","+weatherModel.getSys().getCountry());
        tv_date.setText(Utils.getDate(weatherModel.getDt(),Constants.DATE_FORMATE_BG_DATE_NEW));
        pressure.setText(pressure_str);
        humidity.setText(humidity_str);

    }

    private void loadImageFromUrlToImageView(final ImageView imageView, String imageIconName) {

        if (imageIconName != null) {
            String imageUri = Constants.IMAGE_URL + imageIconName + ".png";
            Log.d(TAG, "ImageURL" + imageUri);

            // To load the image dynamically on-fly
            Glide.with(getActivity()).load(imageUri)
                    .thumbnail(0.5f)
                    .into(imageView);

        } else {
            Log.e(TAG, "Image ICON NOT FOUND");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        double lat = getArguments().getDouble(LAT);
        double lon = getArguments().getDouble(LON);
         viewModel  = ViewModelProviders.of(this, new CityWeatherDetailModelFactory(this.getActivity().getApplication(), lat,lon)).get(CityViewModel.class);

        if (((MainActivity) getActivity()).isNetworkAvailable()) {
            loadCityData();
        } else {
            Toast.makeText(getActivity(), "Internet is not connected", Toast.LENGTH_LONG).show();
            getActivity().finish();
        }

    }

    public static CityFragment forProduct(double lat, double lon) {
        CityFragment fragment = new CityFragment();
        Bundle args = new Bundle();
        args.putDouble(LAT, lat);
        args.putDouble(LON, lon);
        fragment.setArguments(args);
        return fragment;
    }

}
