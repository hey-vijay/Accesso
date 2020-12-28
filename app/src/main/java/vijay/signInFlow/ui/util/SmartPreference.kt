package vijay.signInFlow.ui.util

import android.content.Context
import android.content.SharedPreferences

class SmartPreference(context: Context) {


    init {
        sharedPreferences = context.getSharedPreferences(Const.SHARED_PREF, Context.MODE_PRIVATE)
    }
    companion object {
        private lateinit var smartPreference: SmartPreference
        lateinit var sharedPreferences: SharedPreferences

        fun getInstance(context: Context):SmartPreference{
            return smartPreference
        }

    }

    //Overloaded setter functions for key value pairs calls
    fun saveValue(key: String?, value: String?) {
        val prefsEditor = sharedPreferences.edit()
        prefsEditor.putString(key, value)
        prefsEditor.apply()
    }

    fun getValue(key: String?, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

}