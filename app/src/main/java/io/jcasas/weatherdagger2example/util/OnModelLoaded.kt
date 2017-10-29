package io.jcasas.weatherdagger2example.util

/**
 * Created by jcasas on 10/29/17.
 */
interface OnModelLoaded<M>{

    fun onModelLoaded(model: M?)
}