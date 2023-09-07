package uz.gita.fooddedivery347.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.fooddedivery347.data.model.OrderData

interface AppRepository {
    suspend fun insertAllFoods(): Result<Unit>

    fun addOrders(orderData: OrderData): Flow<Result<String>>

}