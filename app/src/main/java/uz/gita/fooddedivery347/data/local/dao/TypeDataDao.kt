package uz.gita.fooddedivery347.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import uz.gita.fooddedivery347.data.local.entity.TypeDataEntity
import uz.gita.fooddedivery347.data.local.entity.TypeDataWithFoods

@Dao
interface TypeDataDao {
    @Transaction
    @Query("SELECT * FROM type_data ORDER BY id ASC")
    fun getTypeData(): Flow<List<TypeDataWithFoods>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTypeData(typeDataEntity: TypeDataEntity)

    @Transaction
    @Query("SELECT * FROM type_data WHERE id IN (SELECT DISTINCT typeDataId FROM foods WHERE name LIKE '%' || :searchQuery || '%' OR description LIKE '%' || :searchQuery || '%') ORDER BY id ASC")
    fun getTypeDataWithFoodsBySearchQuery(searchQuery: String): Flow<List<TypeDataWithFoods>>

}
