package uz.gita.fooddedivery347.domain.repository.auth

import android.app.Activity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun createUserWithPhone(phone:String, activity: Activity) : Flow<Result<String>>

    fun signWithCredential(otp:String): Flow<Result<String>>
}