package uz.gita.fooddedivery347.data.local.sharedPref

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefImpl @Inject constructor(private val pref: SharedPreferences) : SharedPref {
    override var smsVerification: String
        set(value) { pref.edit().putString("SMS_VERIFICATION", value).apply() }
        get() = pref.getString("SMS_VERIFICATION", "").toString()
    override var name: String
        set(value) { pref.edit().putString("TOKEN", value).apply() }
        get() = pref.getString("TOKEN", "").toString()

    override var hasToken: Boolean
        set(value) { pref.edit().putBoolean("HAS_TOKEN", value).apply() }
        get() = pref.getBoolean("HAS_TOKEN", false)

    override var phone: String
        set(value) { pref.edit().putString("PHONE", value).apply() }
        get() = pref.getString("PHONE", "").toString()

    override var count: Int
        set(value) { pref.edit().putInt("COUNT", value).apply() }
        get() = pref.getInt("COUNT", 0)

    override var sum: Long
        set(value) { pref.edit().putLong("SUMM", value).apply() }
        get() = pref.getLong("SUMM", 0L)

}