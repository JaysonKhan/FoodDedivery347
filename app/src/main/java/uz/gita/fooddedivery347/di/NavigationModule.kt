package uz.gita.fooddedivery347.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.fooddedivery347.utils.navigation.AppNavigator
import uz.gita.fooddedivery347.utils.navigation.NavigationDispatcher
import uz.gita.fooddedivery347.utils.navigation.NavigationHandler

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    fun bindAppNavigator(impl: NavigationDispatcher): AppNavigator

    @Binds
    fun bindNavigationHandler(impl: NavigationDispatcher): NavigationHandler
}


