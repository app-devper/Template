package com.devper.template.common.pref

import android.content.Context
import android.content.SharedPreferences
import com.devper.common.SecurePreferences

class AppPreference private constructor(context: Context) {

    private val sharedpreferences: SharedPreferences

    var appUuid: String
        get() = sharedpreferences.getString(PREF_APP_UUID, "")!!
        set(uuid) {
            val editor = sharedpreferences.edit()
            editor.putString(PREF_APP_UUID, uuid)
            editor.apply()
        }

    var deviceUuid: String
        get() = sharedpreferences.getString(PREF_DEVICE_UUID, "")!!
        set(uuid) {
            val editor = sharedpreferences.edit()
            editor.putString(PREF_DEVICE_UUID, uuid)
            editor.apply()
        }

    var token: String
        get() = sharedpreferences.getString(PREF_TOKEN, "")!!
        set(uuid) {
            val editor = sharedpreferences.edit()
            editor.putString(PREF_TOKEN, uuid)
            editor.apply()
        }

    var fbToken: String?
        get() = sharedpreferences.getString(PREF_FCM_TOKEN, "")
        set(uuid) {
            val editor = sharedpreferences.edit()
            editor.putString(PREF_FCM_TOKEN, uuid)
            editor.apply()
        }

    var userId: String
        get() = sharedpreferences.getString(PREF_USER_ID, "")!!
        set(id) {
            val editor = sharedpreferences.edit()
            editor.putString(PREF_USER_ID, id)
            editor.apply()
        }

    init {
        sharedpreferences = SecurePreferences.Builder(context).filename(PREF_APP).build()
    }

    companion object {

        private const val PREF_APP = "PREF_APP"
        private const val PREF_APP_UUID = "PREF_APP_UUID"
        private const val PREF_DEVICE_UUID = "PREF_DEVICE_UUID"
        private const val PREF_TOKEN = "PREF_TOKEN"
        private const val PREF_FCM_TOKEN = "PREF_FCM_TOKEN"
        private const val PREF_USER_ID = "PREF_USER_ID"

        private var instance: AppPreference? = null

        fun init(context: Context): AppPreference {
            if (instance == null) {
                SecurePreferences.init(context)
                instance = AppPreference(context)
            }
            return instance!!
        }

        fun getInstance(): AppPreference {
            if (instance == null) {
                throw NullPointerException("Null instance. You must call init() before using.")
            }
            return instance!!
        }
    }

}
