package uz.gita.fooddedivery347.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.gita.fooddedivery347.data.local.entity.ParkingSpotEntity

@Dao
interface ParkingSpotDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParkingSpot(spot: ParkingSpotEntity)

    @Delete
    suspend fun deleteParkingSpot(spot: ParkingSpotEntity)

    @Query("SELECT * FROM parkingspotentity")
    fun getParkingSpots(): Flow<List<ParkingSpotEntity>>
}