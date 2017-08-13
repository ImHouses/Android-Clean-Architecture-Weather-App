package io.jcasas.weatherdagger2example.util

/**
 * Created by jcasas on 8/12/17.
 */
object TempConverter {

    fun convert(value:Double, from:Temp, target:Temp):Double {

        if (from == Temp.CELSIUS && target == Temp.KELVIN) {
            return value + 273.15
        }
        if (from == Temp.KELVIN && target == Temp.CELSIUS) {
            return value - 273.15
        }
        if (from == Temp.FAHRENHEIT && target == Temp.CELSIUS) {
            return (value - 32) / 1.8
        }
        if (from == Temp.CELSIUS && target == Temp.FAHRENHEIT) {
            return (value * 1.8) + 32
        }
        if (from == Temp.FAHRENHEIT && target == Temp.KELVIN) {
            return (value + 459.67) * (5 / 9)
        }
        if (from == Temp.KELVIN && target == Temp.FAHRENHEIT) {
            return (value * (5 / 9)) - 459.67
        }
        return value
    }
}