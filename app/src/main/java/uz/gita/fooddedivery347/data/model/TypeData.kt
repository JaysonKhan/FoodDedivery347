package uz.gita.fooddedivery347.data.model

import uz.gita.fooddedivery347.data.local.entity.FoodEntity

data class TypeData(
    val type: String,
    val list: List<FoodEntity>
)
