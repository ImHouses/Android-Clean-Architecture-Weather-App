package io.jcasas.weatherdagger2example.di.component

import dagger.Component
import io.jcasas.weatherdagger2example.di.ActivityScope
import io.jcasas.weatherdagger2example.di.module.AppModule
import io.jcasas.weatherdagger2example.di.module.ViewModelModule
import io.jcasas.weatherdagger2example.ui.main.MainActivity

/**
 * Created by jcasas on 8/12/17.
 */
@ActivityScope
@Component(dependencies = [WeatherAppComponent::class])
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)
}