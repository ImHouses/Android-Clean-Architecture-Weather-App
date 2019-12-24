package io.jcasas.weatherdagger2example.ui.main.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.jcasas.weatherdagger2example.R
import io.jcasas.weatherdagger2example.domain.Units
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
import io.jcasas.weatherdagger2example.util.ActivityUtils
import org.joda.time.format.DateTimeFormat

// TODO Convert to Data Binding.
class ForecastAdapter(private val list: List<ForecastEntity>, var units: Units) :
        RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    inner class ForecastViewHolder : RecyclerView.ViewHolder {

        var mDayText: TextView
        var mIcon: ImageView
        var mTemp: TextView
        var mTempUnits: TextView
        var mWeatherDesc: TextView
        var mMaxTemp: TextView
        var mMinTemp: TextView

        constructor(itemView: View) : super(itemView) {
            mDayText = itemView.findViewById(R.id.forecast_item_date)
            mIcon = itemView.findViewById(R.id.forecast_item_icon)
            mTemp = itemView.findViewById(R.id.forecast_item_temp)
            mTempUnits = itemView.findViewById(R.id.forecast_item_temp_units)
            mWeatherDesc = itemView.findViewById(R.id.forecast_item_desc)
            mMaxTemp = itemView.findViewById(R.id.forecast_item_max)
            mMinTemp = itemView.findViewById(R.id.forecast_item_min)
        }

        fun bindData(forecast: ForecastEntity) {
            val format = "EEEE, MMMM dd"
            val unitsText = if (units == Units.SI) "C" else "F"
            mDayText.text = DateTimeFormat.forPattern(format).print(forecast.date)
            mIcon.setImageResource(ActivityUtils.getIconRes(forecast.id))
            mTemp.text = forecast.temperature.toInt().toString()
            mTempUnits.text = unitsText
            mWeatherDesc.text = forecast.status
            val maxTempString = ActivityUtils.getStringByRes(R.string.forecast_item_max, itemView.context)
            val minTempString = ActivityUtils.getStringByRes(R.string.forecast_item_min, itemView.context)
            mMaxTemp.text = String.format(maxTempString, forecast.maxTemperature.toInt(), unitsText)
            mMinTemp.text = String.format(minTempString, forecast.minTemperature.toInt(), unitsText)
        }
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val itemView: View = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.forecast_item, parent,false)
        return ForecastViewHolder(itemView)
    }
}




