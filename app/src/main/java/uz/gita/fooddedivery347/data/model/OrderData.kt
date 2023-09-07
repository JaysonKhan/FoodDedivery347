package uz.gita.fooddedivery347.data.model

import uz.gita.fooddedivery347.data.local.entity.FoodEntity

data class OrderData(
    val list: List<FoodEntity>,
    val userId: String,
    val comment: String,
    val allPrice: Long
)