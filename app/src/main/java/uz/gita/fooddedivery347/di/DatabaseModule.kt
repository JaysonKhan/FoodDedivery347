package uz.gita.fooddedivery347.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.fooddedivery347.data.local.MyDatabase
import uz.gita.fooddedivery347.data.local.dao.MyDao
import uz.gita.fooddedivery347.data.local.dao.ParkingSpotDao
import uz.gita.fooddedivery347.data.local.dao.TypeDataDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @[Provides Singleton]
    fun provideEventDatabase(@ApplicationContext context: Context): MyDatabase =
        Room
            .databaseBuilder(context, MyDatabase::class.java, MyDatabase.DB_NAME)
            .allowMainThreadQueries()
            .build()

    @[Provides Singleton]
    fun provideEventDao(database: MyDatabase): MyDao = database.getDao()

    @[Provides Singleton]
    fun provideTypeDao(database: MyDatabase): TypeDataDao = database.typeDataDao()

    @[Provides Singleton]
    fun provideSpotDao(database: MyDatabase): ParkingSpotDao = database.parkingSpotDao()

}