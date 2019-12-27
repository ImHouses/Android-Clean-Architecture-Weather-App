/*
 * Copyright 2019, Juan Casas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.jcasas.weatherdagger2example.ui.main.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.jcasas.weatherdagger2example.databinding.ForecastItemBinding
import io.jcasas.weatherdagger2example.domain.Units
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
import io.jcasas.weatherdagger2example.util.ActivityUtils
import kotlinx.android.synthetic.main.forecast_item.view.*

class ForecastAdapter(private val list: List<ForecastEntity>, var units: Units) :
        RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    inner class ForecastViewHolder(
            private val binding: ForecastItemBinding
    ) :RecyclerView.ViewHolder(binding.root) {

        fun bindData(forecast: ForecastEntity) {
            binding.apply {
                this.forecast = forecast
                this.units = this@ForecastAdapter.units
                root.forecast_item_icon.setImageResource(ActivityUtils.getIconRes(forecast.id))
            }.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ForecastItemBinding.inflate(layoutInflater, parent, false)
        return ForecastViewHolder(binding)
    }
}




