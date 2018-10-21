

package com.weatherapp.ui.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.weatherapp.R;
import com.weatherapp.models.ForecastModel;
import com.weatherapp.databinding.ForecastListFragmentBinding;
import com.weatherapp.ui.adapters.ForecastAdapter;
import com.weatherapp.utils.Constants;
import com.weatherapp.viewmodel.CityWeatherViewModel;
import com.weatherapp.viewmodel.modelFactory.MyViewModelFactory;

public class CityForecastFragment extends Fragment {

    public static final String TAG = "CityForecastListViewModel";

    private ForecastAdapter mCityAdapter;

    private ForecastListFragmentBinding mBinding;
    private static final String LAT = "lat";
    private static final String LON = "lon";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.forecast_list_fragment, container, false);

        mBinding.forecastList.setAdapter(mCityAdapter);

        return mBinding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void generateUI(ForecastModel weatherModel) {
//        tv_temp.setText(weatherModel.getList().get() + "");
//        tv_climate_description.setText(weatherModel.getWeather()[0].getMain() + ", " + weatherModel.getWeather()[0].getDescription());
//        loadImageFromUrlToImageView(weather_icon, weatherModel.getWeather()[0].getIcon());
    }

    @SuppressLint("LongLogTag")
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
         CityWeatherViewModel viewModel  = ViewModelProviders.of(this, new MyViewModelFactory(this.getActivity().getApplication(), lat,lon)).get(CityWeatherViewModel.class);
        subscribeUi(viewModel);

    }

    private void subscribeUi(CityWeatherViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getForecast().observe(this, new Observer<ForecastModel>() {
            @Override
            public void onChanged(@Nullable ForecastModel data) {
                if (data != null) {
                    mBinding.setIsLoading(false);
                    mCityAdapter = new ForecastAdapter(getActivity(),data);
                    mBinding.forecastList.setAdapter(mCityAdapter);

                } else {
                    mBinding.setIsLoading(true);
                }
                // espresso does not know how to wait for data binding's loop so we execute changes
                // sync.
                mBinding.executePendingBindings();
            }


        });


    }


    public static CityForecastFragment forProduct(double lat, double lon) {
        CityForecastFragment fragment = new CityForecastFragment();
        Bundle args = new Bundle();
        args.putDouble(LAT, lat);
        args.putDouble(LON, lon);
        fragment.setArguments(args);
        return fragment;
    }

}
