package io.jcasas.weatherdagger2example.di.module

import dagger.Module
import dagger.Provides
import io.jcasas.weatherdagger2example.data.source.DataManager
import io.jcasas.weatherdagger2example.ui.main.MainActivityContract
import io.jcasas.weatherdagger2example.ui.main.MainActivityPresenter

/**
 * Created by jcasas on 8/12/17.
 */
@Module
class MainActivityModule(val view:MainActivityContract.View) {

    @Provides
    fun providePresenter(dataManager:DataManager): MainActivityContract.Presenter {
        return MainActivityPresenter(view, dataManager)
    }
}