package uz.gita.fooddedivery347.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import uz.gita.fooddedivery347.data.local.entity.FoodEntity

@Dao
interface MyDao {

    @Query("Select exists (select * from foods)")
    fun isInitialized(): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFoodToBasket(food:FoodEntity)

    @Query("SELECT * FROM foods WHERE typeDataId=:typeID")
    fun getFoodsFromType(typeID:Int): Flow<List<FoodEntity>>

    @Query("SELECT * FROM foods WHERE count>0")
    fun getFoods(): Flow<List<FoodEntity>>

    @Query("SELECT * FROM foods WHERE id=:foodID")
    fun getFoodDetail(foodID:Int): Flow<FoodEntity>

    @Update
    fun update(foodEntity: FoodEntity)

    @Query("DELETE FROM foods")
    fun clearData()

    @Query("SELECT SUM(cost * count) as totalSum FROM foods")
    fun getTotalSum(): Long

    @Query("UPDATE foods SET count = count - 1 WHERE id = :foodId AND count > 0")
    suspend fun minusCount(foodId: Int)

    @Query("UPDATE foods SET count = count + 1 WHERE id = :foodId AND count < 10")
    suspend fun plusCount(foodId: Int)

}