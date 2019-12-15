package io.jcasas.weatherdagger2example.ui.main

import android.content.Context
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.jcasas.weatherdagger2example.R
import com.juancasasm.android.weatherexample.domain.Forecast
import io.jcasas.weatherdagger2example.util.ActivityUtils
import io.jcasas.weatherdagger2example.util.Temp
import io.jcasas.weatherdagger2example.util.TempConverter

class ForecastAdapter(val list:List<Forecast>, val context:Context) :
        RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    class ForecastViewHolder : RecyclerView.ViewHolder {

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

        fun bindData(forecast: Forecast) {
            mDayText.setText(ActivityUtils.getDayString(forecast.dt))
            mIcon.setImageResource(ActivityUtils.getIconRes(forecast.weather[0].id))
            mTemp.text = TempConverter.convert(forecast.temp.day, Temp.KELVIN, Temp.CELSIUS).toInt().toString()
            mTempUnits.text = "°C"
            mWeatherDesc.text = forecast.weather[0].main
            val maxTempString = ActivityUtils.getStringByRes(R.string.forecast_item_max, itemView.context)
            val minTempString = ActivityUtils.getStringByRes(R.string.forecast_item_min, itemView.context)
            mMaxTemp.text = String.format(maxTempString, getCelsius(forecast.temp.max).toInt())
            mMinTemp.text = String.format(minTempString, getCelsius(forecast.temp.min).toInt())
        }

        private fun getCelsius(temp: Double): Double {
            return TempConverter.convert(temp, Temp.KELVIN, Temp.CELSIUS)
        }
    }

    class VerticalSpaceItemDecoration(val verticalSpace: Int) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            if (parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount -1) {
                outRect.bottom = verticalSpace
            }
        }
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.forecast_item, parent,false)
        return ForecastViewHolder(itemView)
    }
}




