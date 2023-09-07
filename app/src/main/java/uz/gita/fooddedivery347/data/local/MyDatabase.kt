package uz.gita.fooddedivery347.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.fooddedivery347.data.local.dao.MyDao
import uz.gita.fooddedivery347.data.local.dao.ParkingSpotDao
import uz.gita.fooddedivery347.data.local.dao.TypeDataDao
import uz.gita.fooddedivery347.data.local.entity.FoodEntity
import uz.gita.fooddedivery347.data.local.entity.ParkingSpotEntity
import uz.gita.fooddedivery347.data.local.entity.TypeDataEntity

@Database(entities = [TypeDataEntity::class,FoodEntity::class, ParkingSpotEntity::class], version = 1, exportSchema = false)
abstract class MyDatabase:RoomDatabase() {
    abstract fun getDao(): MyDao
    abstract fun typeDataDao(): TypeDataDao
    abstract fun parkingSpotDao(): ParkingSpotDao

    companion object{
        val DB_NAME = "database"
    }
}