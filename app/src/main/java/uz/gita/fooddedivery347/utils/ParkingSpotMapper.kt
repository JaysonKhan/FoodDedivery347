package uz.gita.fooddedivery347.utils

import uz.gita.fooddedivery347.data.local.entity.ParkingSpotEntity
import uz.gita.fooddedivery347.data.model.ParkingSpot


fun ParkingSpotEntity.toParkingSpot(): ParkingSpot {
    return ParkingSpot(
        lat = lat,
        lng = lng,
        id = id
    )
}

fun ParkingSpot.toParkingSpotEntity(): ParkingSpotEntity {
    return ParkingSpotEntity(
        lat = lat,
        lng = lng,
        id = id
    )
}