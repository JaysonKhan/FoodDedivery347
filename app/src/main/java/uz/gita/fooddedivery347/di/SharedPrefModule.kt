package uz.gita.fooddedivery347.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.fooddedivery347.data.local.sharedPref.SharedPref
import uz.gita.fooddedivery347.data.local.sharedPref.SharedPrefImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPrefModule {

    @Provides
    @Singleton
    fun providesSharedPref(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("SHARED_PREFERENCE", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesMySharedPref(sh: SharedPreferences): SharedPref = SharedPrefImpl(sh)
}