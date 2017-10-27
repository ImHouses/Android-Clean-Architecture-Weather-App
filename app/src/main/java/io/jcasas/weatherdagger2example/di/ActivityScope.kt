package io.jcasas.weatherdagger2example.di

import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Created by jcasas on 10/26/17.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Scope
annotation class ActivityScope