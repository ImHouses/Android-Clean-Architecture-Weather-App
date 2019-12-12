/*
 * Copyright 2017, Juan Casas
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

package io.jcasas.weatherdagger2example.util

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import io.jcasas.weatherdagger2example.R
import java.util.*

/**
 * Created by jcasas on 8/12/17.
 */
object ActivityUtils {

    fun createStandardAlert(title:Int, message:Int, context: Context):AlertDialog {
        return AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.accept_button_string) { dialogInterface: DialogInterface, _: Int -> dialogInterface!!.dismiss() }
                .create()
    }

    fun getStringByRes(stringId:Int, context: Context):String {
        return context.getString(stringId)
    }

    fun getIconRes(id:Int):Int = when(id) {
        in 200..232 -> R.drawable.ic_storm
        in 300..321 -> R.drawable.ic_raining
        in 300..321 -> R.drawable.ic_raining
        in 500..504 -> R.drawable.ic_light_rain
        511 -> R.drawable.ic_hail
        in 520..531 -> R.drawable.ic_raining
        // TODO Change for Snow
        in 600..622 -> R.drawable.ic_cloud
        //
        in 701..781 -> R.drawable.ic_cloud
        800 -> R.drawable.ic_sun
        801 -> R.drawable.ic_cloud
        in 802..804 -> R.drawable.ic_cloud
        else -> 0
    }

    fun getDayString(date: Long): Int {
        val date: Date = Date(date * 1000)
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        return when(calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY-> R.string.monday
            Calendar.TUESDAY-> R.string.tuesday
            Calendar.WEDNESDAY-> R.string.wednesday
            Calendar.THURSDAY-> R.string.thursday
            Calendar.FRIDAY-> R.string.friday
            Calendar.SATURDAY-> R.string.saturday
            Calendar.SUNDAY-> R.string.sunday
            else-> -1
        }
    }
}