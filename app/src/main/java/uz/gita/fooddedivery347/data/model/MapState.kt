package uz.gita.fooddedivery347.data.model

import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties
import uz.gita.fooddedivery347.utils.MapStyle

data class MapState(
    var properties: MapProperties = MapProperties(mapStyleOptions = MapStyleOptions(MapStyle.json), isMyLocationEnabled = true, isBuildingEnabled = true),
    val parkingSpots: List<ParkingSpot> = emptyList()
)
