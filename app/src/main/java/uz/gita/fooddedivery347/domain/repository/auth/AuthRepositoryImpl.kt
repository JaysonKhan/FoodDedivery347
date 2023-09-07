package uz.gita.fooddedivery347.domain.repository.auth

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uz.gita.fooddedivery347.data.local.sharedPref.SharedPref
import uz.gita.fooddedivery347.utils.myLog
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val sharedPref: SharedPref
) : AuthRepository {
    private val authdb: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var omVerificationCode: String

    override fun createUserWithPhone(phone: String, activity: Activity): Flow<Result<String>> = callbackFlow {

            val onVerificationCallback =
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        trySend(Result.failure(p0))
                    }

                    override fun onCodeSent(
                        verificationCode: String,
                        p1: PhoneAuthProvider.ForceResendingToken
                    ) {
                        super.onCodeSent(verificationCode, p1)
                        myLog("Sms Code -> $verificationCode")
                        trySend(Result.success("OTP Sent Successfully"))
                        sharedPref.smsVerification = verificationCode
                        omVerificationCode = verificationCode

                    }
                }

            val options = PhoneAuthOptions.newBuilder(authdb)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(onVerificationCallback)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
            awaitClose {
                close()
            }
        }

    override fun signWithCredential(otp: String): Flow<Result<String>> = callbackFlow {
        omVerificationCode = sharedPref.smsVerification
        val credential = PhoneAuthProvider.getCredential(omVerificationCode, otp)
        authdb.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    trySend(Result.success("otp verified"))
            }.addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose {
            close()
        }
    }
}