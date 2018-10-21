package com.weatherapp.ui.fragments;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weatherapp.R;
import com.weatherapp.databinding.CityListFragmentBinding;
import com.weatherapp.db.entity.CityData;
import com.weatherapp.ui.activities.MainActivity;
import com.weatherapp.ui.adapters.CityAdapter;
import com.weatherapp.ui.callbacks.CityClickCallback;
import com.weatherapp.viewmodel.CityListViewModel;

import java.util.List;

public class CityListFragment extends Fragment {

    public static final String TAG = "CityListViewModel";

    private CityAdapter mCityAdapter;

    private CityListFragmentBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.city_list_fragment, container, false);

        mCityAdapter = new CityAdapter(mCityClickCallback,getActivity());
        mBinding.productsList.setAdapter(mCityAdapter);

        return mBinding.getRoot();
    }
    private final CityClickCallback mCityClickCallback = new CityClickCallback() {

        @Override
        public void onClick(CityData product) {

            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show(product);
            }
        }
    };
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final CityListViewModel viewModel =
                ViewModelProviders.of(this).get(CityListViewModel.class);

        subscribeUi(viewModel);
    }

    private void subscribeUi(CityListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getCities().observe(this, new Observer<List<CityData>>() {
            @Override
            public void onChanged(@Nullable List<CityData> myProducts) {
                if (myProducts != null) {
                    mBinding.setIsLoading(false);
                    mCityAdapter.setCityList(myProducts);
                } else {
                    mBinding.setIsLoading(true);
                }
                // espresso does not know how to wait for data binding's loop so we execute changes
                // sync.
                mBinding.executePendingBindings();
            }
        });
    }


}