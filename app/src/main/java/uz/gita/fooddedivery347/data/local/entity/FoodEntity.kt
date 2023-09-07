package uz.gita.fooddedivery347.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "type_data")
data class TypeDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: String,
)

@Entity(tableName = "foods",
    foreignKeys = [
        ForeignKey(
            entity = TypeDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["typeDataId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FoodEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String,
    val description: String,
    val cost: Long,
    val image: String,
    var count: Int = 0,
    val typeDataId: Long
)

data class TypeDataWithFoods(
    @Embedded val typeData: TypeDataEntity,
    @Relation(parentColumn = "id", entityColumn = "typeDataId")
    val foods: List<FoodEntity>
)
