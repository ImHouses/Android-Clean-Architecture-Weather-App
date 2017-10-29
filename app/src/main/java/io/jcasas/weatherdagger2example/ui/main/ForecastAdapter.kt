package io.jcasas.weatherdagger2example.ui.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import io.jcasas.weatherdagger2example.data.source.model.Forecast

/**
 * Created by jcasas on 10/6/17.
 */
class ForecastAdapter(list:List<Forecast>, context:Context) :
        RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    class ForecastViewHolder : RecyclerView.ViewHolder {

        constructor(itemView: View) : super(itemView) {
            
        }

    }

    override fun onBindViewHolder(holder: ForecastViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ForecastViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}




