package io.jcasas.weatherdagger2example.di.component

import dagger.Component
import io.jcasas.weatherdagger2example.di.ActivityScope
import io.jcasas.weatherdagger2example.ui.main.MainActivity

/**
 * Created by jcasas on 8/12/17.
 */
@ActivityScope
@Component(dependencies = arrayOf(WeatherAppComponent::class), modules = arrayOf())
interface MainActivityComponent {

    fun inject(mainActivity:MainActivity)

}