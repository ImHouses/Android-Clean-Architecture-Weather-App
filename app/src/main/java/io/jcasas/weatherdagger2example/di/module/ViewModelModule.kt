package io.jcasas.weatherdagger2example.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.jcasas.weatherdagger2example.di.ViewModelFactory
import io.jcasas.weatherdagger2example.di.ViewModelKey
import io.jcasas.weatherdagger2example.ui.main.MainViewModel

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(viewModel: MainViewModel): ViewModel
}