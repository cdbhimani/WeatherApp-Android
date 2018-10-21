package com.weatherapp.ui.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weatherapp.R;
import com.weatherapp.models.ForecastList;
import com.weatherapp.models.ForecastModel;
import com.weatherapp.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ForecastAdapter extends  RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private static final String TAG = " AccountStatementList";
    private final Activity mContext;
    private final LayoutInflater inflater;
    private List<ForecastList> foreCastList;
    private ForecastModel  forecastData;

    public ForecastAdapter(Activity activity, ForecastModel list) {
        this.mContext = activity;
        this.foreCastList = list.getList();
        forecastData = list;
        this.inflater = LayoutInflater.from(mContext);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.forecast_city_item1, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ForecastList forcastData = foreCastList.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE MMM dd HH:mm");
        Date netDate = (new Date(Long.parseLong(String.valueOf(forcastData.getDt())) * 1000L));
        
        holder.txtForcastDay.setText(sdf.format(netDate));
        holder.imgFrocastWeatherStatus.setBackgroundResource(Utils.getIcon60(forcastData.getWeather().get(0).getIcon()));
        holder.txtForcastMaxTemp.setText(Utils.convertWeatherUnit(mContext, String.valueOf(forcastData.getMain().getTempMax())));
        holder.txtForcastMinTemp.setText(Utils.convertWeatherUnit(mContext, String.valueOf(forcastData.getMain().getTempMin())));

        try {
            if(forcastData.getMain().getHumidity() > 0){
                String humidity = forcastData.getMain().getHumidity() + " " + mContext.getResources().getString(R.string.humidity_symbol);
                final SpannableStringBuilder hsb = new SpannableStringBuilder(humidity);
                final ForegroundColorSpan hfcs = new ForegroundColorSpan(mContext.getResources().getColor(R.color.bgtextOffWhite));
                hsb.setSpan(hfcs, (humidity.length() - 1), (humidity.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                holder.txtFrocastHumidityInfo.setText(hsb);
            }else{
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        String windSpeed = Utils.convertSpeed(mContext, String.valueOf(forcastData.getWind().getSpeed())) + " " + Utils.convertSpeedUnit(mContext);
        final SpannableStringBuilder wsb = new SpannableStringBuilder(windSpeed);
        final ForegroundColorSpan wfcs = new ForegroundColorSpan(mContext.getResources().getColor(R.color.bgtextOffWhite));
        wsb.setSpan(wfcs, (windSpeed.length() - Utils.convertSpeedUnit(mContext).length()), (windSpeed.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        holder.txtFrocastWindInfo.setText(wsb);

        String pressure = Utils.convertPressure(mContext, String.valueOf(forcastData.getMain().getPressure())) + " " + Utils.convertPressureUnit(mContext);
        final SpannableStringBuilder psb = new SpannableStringBuilder(pressure);
        final ForegroundColorSpan pfcs = new ForegroundColorSpan(mContext.getResources().getColor(R.color.bgtextOffWhite));
        psb.setSpan(pfcs, (pressure.length() - Utils.convertPressureUnit(mContext).length()), (pressure.length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        holder.txtFrocastPressureInfo.setText(psb);
//            holder.txtHumidity.setText(listDaily.getHumidity() + " " + mContext.getResources().getString(R.string.humidity_symbol));
//            holder.txtWindSpeed.setText(Utils.convertSpeed(mContext, listDaily.getWindSpeed()) + " " + Utils.convertSpeedUnit(mContext));
//            holder.txtPressure.setText(Utils.convertPressure(mContext, listDaily.getPressure()) + " " + Utils.convertPressureUnit(mContext));
        holder.txtForcastDescription.setText(forcastData.getWeather().get(0).getDescription().substring(0, 1).toUpperCase() + forcastData.getWeather().get(0).getDescription().substring(1).toLowerCase());

    }


    @Override
    public int getItemCount() {
        return foreCastList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtForcastDay;
        private TextView txtForcastMaxTemp;
        private TextView txtForcastMinTemp;
        private ImageView imgFrocastWeatherStatus;
        private TextView txtFrocastHumidityInfo;
        private TextView txtFrocastWindInfo;
        private TextView txtFrocastPressureInfo;
        private TextView txtForcastDescription;


        public ViewHolder(View view) {
            super(view);
            txtForcastDay = (TextView) view.findViewById(R.id.txtForcastDay);
            txtForcastMaxTemp = (TextView) view.findViewById(R.id.txtForcastMaxTemp);
            txtForcastMinTemp = (TextView) view.findViewById(R.id.txtForcastMinTemp);
            imgFrocastWeatherStatus = (ImageView) view.findViewById(R.id.imgFrocastWeatherStatus);
            txtFrocastHumidityInfo = (TextView) view.findViewById(R.id.txtFrocastHumidityInfo);
            txtFrocastWindInfo = (TextView) view.findViewById(R.id.txtFrocastWindInfo);
            txtFrocastPressureInfo = (TextView) view.findViewById(R.id.txtFrocastPressureInfo);
            txtForcastDescription = (TextView) view.findViewById(R.id.txtForcastDescription);
        }
    }

}
