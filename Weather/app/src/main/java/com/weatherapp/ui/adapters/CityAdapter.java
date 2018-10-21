package com.weatherapp.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weatherapp.R;
import com.weatherapp.databinding.CityItemBinding;
import com.weatherapp.db.entity.CityData;
import com.weatherapp.ui.callbacks.CityClickCallback;
import com.weatherapp.ui.fragments.CityForecastFragment;

import java.util.List;
import java.util.Objects;


public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    List<? extends CityData> mCityList;
    Context mConrtext;
    @Nullable
    private final CityClickCallback mCityClickCallback;

    public CityAdapter(@Nullable CityClickCallback clickCallback, Context activity) {
        mCityClickCallback = clickCallback;
        mConrtext = activity;
    }

    public void setCityList(final List<? extends CityData> cityList) {
        if (mCityList == null) {
            mCityList = cityList;
            notifyItemRangeInserted(0, cityList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mCityList.size();
                }

                @Override
                public int getNewListSize() {
                    return cityList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mCityList.get(oldItemPosition).getUid() ==
                            cityList.get(newItemPosition).getUid();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    CityData newProduct = cityList.get(newItemPosition);
                    CityData oldProduct = mCityList.get(oldItemPosition);
                    return newProduct.getUid() == oldProduct.getUid()
                            && Objects.equals(newProduct.getName(), oldProduct.getName());
                }
            });
            mCityList = cityList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CityItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.city_item,
                        parent, false);
        binding.setCallback(mCityClickCallback);

        return new CityViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        holder.binding.setCity(mCityList.get(position));
        holder.binding.executePendingBindings();
        holder.binding.imgForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityForecastFragment productFragment = CityForecastFragment.forProduct(mCityList.get(position).getLatitude(),mCityList.get(position).getLongitude());

                ((FragmentActivity)mConrtext).getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("cityforecast")
                        .replace(R.id.fragment_container,
                                productFragment, null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCityList == null ? 0 : mCityList.size();
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder {

        final CityItemBinding binding;

        public CityViewHolder(CityItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
