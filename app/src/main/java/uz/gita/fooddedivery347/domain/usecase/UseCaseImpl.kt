package uz.gita.fooddedivery347.domain.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import uz.gita.fooddedivery347.data.local.dao.MyDao
import uz.gita.fooddedivery347.data.local.dao.ParkingSpotDao
import uz.gita.fooddedivery347.data.local.dao.TypeDataDao
import uz.gita.fooddedivery347.data.local.entity.FoodEntity
import uz.gita.fooddedivery347.data.local.entity.TypeDataEntity
import uz.gita.fooddedivery347.data.local.entity.TypeDataWithFoods
import uz.gita.fooddedivery347.data.model.OrderData
import uz.gita.fooddedivery347.data.model.ParkingSpot
import uz.gita.fooddedivery347.data.model.TypeData
import uz.gita.fooddedivery347.domain.repository.AppRepository
import uz.gita.fooddedivery347.utils.toParkingSpot
import uz.gita.fooddedivery347.utils.toParkingSpotEntity
import javax.inject.Inject

class UseCaseImpl @Inject constructor(
    private val repository: AppRepository,
    private val dao: MyDao,
    private val bigDao: TypeDataDao,
    private val parkingSpotDao: ParkingSpotDao
) : UseCase {
    var list = arrayListOf<TypeData>()

    override suspend fun insertAllFoods(): Result<Unit> = repository.insertAllFoods()

    override fun addOrders(orderData: OrderData): Flow<Result<String>> =
        repository.addOrders(orderData)

//    override fun getFoodCount(id: Int): Int = dao.getFoodCount(id)

    override fun add(food: FoodEntity) = dao.addFoodToBasket(food)

    override fun updateFood(foodEntity: FoodEntity) = dao.update(
        FoodEntity(
            id = foodEntity.id,
            name = foodEntity.name,
            cost = foodEntity.cost,
            count = foodEntity.count,
            image = foodEntity.image,
            description = foodEntity.description,
            typeDataId = foodEntity.typeDataId
        )
    )

    override fun clearData(): Flow<Unit> = flow {
        dao.clearData()
        emit(Unit)
    }.flowOn(Dispatchers.IO)

    override fun getTotalSum(): Long = dao.getTotalSum()
    override fun getFoods(): Flow<List<FoodEntity>> = dao.getFoods()
    override fun getFoodDetail(foodID: Int): Flow<FoodEntity> = dao.getFoodDetail(foodID)

    override fun getFoodsFromType(typeID: Int): Flow<List<FoodEntity>> =
        dao.getFoodsFromType(typeID)

    override fun getTypeData(): Flow<List<TypeDataWithFoods>> = bigDao.getTypeData()
    override fun getTypeDataWithFoodsBySearchQuery(searchQuery: String): Flow<List<TypeDataWithFoods>> =
        bigDao.getTypeDataWithFoodsBySearchQuery(searchQuery)

    override suspend fun minusCount(foodId: Int) = dao.minusCount(foodId)

    override suspend fun plusCount(foodId: Int) = dao.plusCount(foodId)
    override suspend fun insertParkingSpot(spot: ParkingSpot) =
        parkingSpotDao.insertParkingSpot(spot.toParkingSpotEntity())

    override suspend fun deleteParkingSpot(spot: ParkingSpot) =
        parkingSpotDao.deleteParkingSpot(spot.toParkingSpotEntity())

    override fun getParkingSpots(): Flow<List<ParkingSpot>> =
        parkingSpotDao.getParkingSpots().map { spots ->
            spots.map {
                it.toParkingSpot()
            }
        }
}