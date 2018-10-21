

package com.weatherapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weatherapp.R;
import com.weatherapp.db.AppDatabase;
import com.weatherapp.utils.Constants;

import java.util.Objects;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    public boolean WEATHER_UNIT_CELSIUS = false;
    public boolean PRESSURE_UNIT_INCHES = false;
    public boolean SPEED_UNIT_KM = false;
    private TextView txtCelc = null;
    private TextView txtFeran = null;
    private TextView txtKm = null;
    private TextView txtMils = null;
    private TextView txtMillibar = null;
    private TextView txtInch = null;
    private TextView txtresetCityList;
    public static final String TAG = "Setting Fragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,
                container, false);
        txtCelc = (TextView) view.findViewById(R.id.celc_setting);
        txtFeran = (TextView) view.findViewById(R.id.feran_setting);
        txtKm = (TextView) view.findViewById(R.id.kilometers_setting);
        txtMils = (TextView) view.findViewById(R.id.miles_setting);
        txtMillibar = (TextView) view.findViewById(R.id.millibar_setting);
        txtInch = (TextView) view.findViewById(R.id.inch_setting);
        txtresetCityList = view.findViewById(R.id.txtresetCityList);
        txtCelc.setOnClickListener(this);
        txtFeran.setOnClickListener(this);
        txtKm.setOnClickListener(this);
        txtMils.setOnClickListener(this);
        txtMillibar.setOnClickListener(this);
        txtInch.setOnClickListener(this);
        txtresetCityList.setOnClickListener(this);
        if (Constants.getSharedPreBoolean(Objects.requireNonNull(getActivity()), Constants.WEATHER_UNIT_CELSIUS_KEY)) {
            calcOnClick();
        } else {
            feranOnClick();
        }

        if (Constants.getSharedPreBoolean(Objects.requireNonNull(getActivity()), Constants.SPEED_UNIT_KM_KEY)) {
            kmOnClick();
        } else {
            milesOnClick();
        }

        if (Constants.getSharedPreBoolean(Objects.requireNonNull(getActivity()), Constants.PRESSURE_UNIT_INCHES_KEY)) {
            inchOnClick();
        } else {
            millibarOnClick();
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.celc_setting:
                calcOnClick();
                break;
            case R.id.feran_setting:
                feranOnClick();
                break;
            case R.id.kilometers_setting:
                kmOnClick();
                break;
            case R.id.miles_setting:
                milesOnClick();
                break;
            case R.id.millibar_setting:
                millibarOnClick();
                break;
            case R.id.inch_setting:
                inchOnClick();
                break;
            case R.id.txtresetCityList:
                AppDatabase.getAppDatabase(getActivity()).cityDao().deleteCities();
                txtresetCityList.setEnabled(false);
                txtresetCityList.setAlpha(0.5f);
                break;
            default:
                break;
        }
    }
    private void calcOnClick() {
        WEATHER_UNIT_CELSIUS = true;
        Constants.setSharedPreBoolean(Objects.requireNonNull(getActivity()), Constants.WEATHER_UNIT_CELSIUS_KEY, WEATHER_UNIT_CELSIUS);
        changeBgColorAndTextColor(txtCelc, txtFeran);
    }

    private void feranOnClick() {
        WEATHER_UNIT_CELSIUS = false;
        Constants.setSharedPreBoolean(Objects.requireNonNull(getActivity()), Constants.WEATHER_UNIT_CELSIUS_KEY, WEATHER_UNIT_CELSIUS);
        changeBgColorAndTextColor(txtFeran, txtCelc);
    }

    private void kmOnClick() {
        SPEED_UNIT_KM = true;
        Constants.setSharedPreBoolean(Objects.requireNonNull(getActivity()), Constants.SPEED_UNIT_KM_KEY, SPEED_UNIT_KM);
        changeBgColorAndTextColor(txtKm, txtMils);
    }

    private void milesOnClick() {
        SPEED_UNIT_KM = false;
        Constants.setSharedPreBoolean(Objects.requireNonNull(getActivity()), Constants.SPEED_UNIT_KM_KEY, SPEED_UNIT_KM);
        changeBgColorAndTextColor(txtMils, txtKm);
    }

    private void millibarOnClick() {
        PRESSURE_UNIT_INCHES = false;
        Constants.setSharedPreBoolean(Objects.requireNonNull(getActivity()), Constants.PRESSURE_UNIT_INCHES_KEY, PRESSURE_UNIT_INCHES);
        changeBgColorAndTextColor(txtMillibar, txtInch);
    }

    private void inchOnClick() {
        PRESSURE_UNIT_INCHES = true;
        Constants.setSharedPreBoolean(Objects.requireNonNull(getActivity()), Constants.PRESSURE_UNIT_INCHES_KEY, PRESSURE_UNIT_INCHES);
        changeBgColorAndTextColor(txtInch, txtMillibar);
    }

    private void changeBgColorAndTextColor(TextView first, TextView second) {
        first.setBackgroundResource(R.drawable.grey_circle);
        first.setTextColor(getResources().getColor(R.color.text_white_bg_color_setting));
        second.setBackgroundResource(R.drawable.black_circle);
        second.setTextColor(getResources().getColor(R.color.text_black_bg_color_setting));
    }
}
