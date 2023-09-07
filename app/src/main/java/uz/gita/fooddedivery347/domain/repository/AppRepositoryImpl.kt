package uz.gita.fooddedivery347.domain.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import uz.gita.fooddedivery347.data.local.dao.MyDao
import uz.gita.fooddedivery347.data.local.dao.TypeDataDao
import uz.gita.fooddedivery347.data.local.entity.FoodEntity
import uz.gita.fooddedivery347.data.local.entity.TypeDataEntity
import uz.gita.fooddedivery347.data.model.Document
import uz.gita.fooddedivery347.data.model.OrderData
import uz.gita.fooddedivery347.data.model.TypeData
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val db:FirebaseFirestore,
    private val dao: MyDao,
    private val bigDao:TypeDataDao
) : AppRepository {
    val fastFoods = arrayListOf<TypeData>()


    override suspend fun insertAllFoods(): Result<Unit>  {
        try {
            val querySnapshot = db.collection("maxway").get().await()
            if(querySnapshot.isEmpty) return Result.failure(Exception("Empty documents"))
            querySnapshot.documents.forEach { snapshot ->
                val title = snapshot["type"] as String
                val id = snapshot["id"] as Long
                bigDao.insertTypeData(TypeDataEntity(id, title))

                snapshot.reference.collection("foods").get()
                    .addOnSuccessListener { querySnapshot ->
                        querySnapshot.documents.forEach { book ->
                            val newFood = FoodEntity(
                                name = book.get("name") as String,
                                description = book.get("description") as String,
                                cost = book.get("cost") as Long,
                                image = book.get("image") as String,
                                typeDataId = id
                            )
                            newFood.id = "${newFood.name}+${newFood.count}".hashCode()
                            dao.addFoodToBasket(newFood)
                        }
                    }.await()
            }
            return Result.success(Unit)

        }catch (e: Exception) {
            return Result.failure(e)
        }
    }


    override  fun addOrders(
        orderData: OrderData
    ): Flow<Result<String>> = callbackFlow {
        var documentId: String
        val a = db.collection("Orders")
            .get()
            .await()

        db.collection("orders")
            .add(
                Document(
                    userId = orderData.userId,
                    comment = orderData.comment,
                    allPrice = orderData.allPrice
                )
            ).addOnSuccessListener {
                documentId = it.id
                orderData.list.forEach { foodEntity ->
                    Log.d("MMM","Repo -> $foodEntity")
                    db.collection("orders")
                        .document(documentId)
                        .collection("foods")
                        .add(foodEntity)
                }
            }.await()

        a.documents.forEach { documentSnapshot ->
            if (orderData.userId == documentSnapshot.get("userid")) {
                orderData.list.forEach {
                    documentSnapshot.reference.collection("foods")
                        .add(it)
                        .await()
                }
            }
        }
        trySend(Result.success("Buyurtmangiz qabul qilindi"))
        awaitClose()
    }.flowOn(Dispatchers.IO)



}