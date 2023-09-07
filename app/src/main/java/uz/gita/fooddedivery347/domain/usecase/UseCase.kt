package uz.gita.fooddedivery347.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.fooddedivery347.data.local.entity.FoodEntity
import uz.gita.fooddedivery347.data.local.entity.TypeDataWithFoods
import uz.gita.fooddedivery347.data.model.OrderData
import uz.gita.fooddedivery347.data.model.ParkingSpot

interface UseCase {
    suspend fun insertAllFoods(): Result<Unit>

    fun addOrders(orderData: OrderData): Flow<Result<String>>

    //    fun getFoodCount(id:Int):Int
    fun add(food: FoodEntity)
    fun updateFood(foodEntity: FoodEntity)
    fun clearData(): Flow<Unit>
    fun getTotalSum(): Long

    fun getFoods(): Flow<List<FoodEntity>>
    fun getFoodDetail(foodID: Int): Flow<FoodEntity>

    fun getFoodsFromType(typeID: Int): Flow<List<FoodEntity>>

    fun getTypeData(): Flow<List<TypeDataWithFoods>>

    fun getTypeDataWithFoodsBySearchQuery(searchQuery: String): Flow<List<TypeDataWithFoods>>

    suspend fun minusCount(foodId: Int)

    suspend fun plusCount(foodId: Int)


    suspend fun insertParkingSpot(spot: ParkingSpot)

    suspend fun deleteParkingSpot(spot: ParkingSpot)

    fun getParkingSpots(): Flow<List<ParkingSpot>>

}