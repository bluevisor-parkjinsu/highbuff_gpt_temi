package kr.bluevisor.robot.highbuff_gpt_temi.util

import android.content.Context
import android.content.SharedPreferences

class SharedPrefUtil(mContext: Context) {

    private var appWidgetPref = "app_pref"

    private var mSharedPreferences: SharedPreferences = mContext.getSharedPreferences(appWidgetPref, Context.MODE_PRIVATE)
    private var mEditor: SharedPreferences.Editor = mSharedPreferences.edit()

    fun saveString(key: String, value: String) {
        mEditor.putString(key, value).apply()
    }


    fun saveStringCommit(key: String, value: String) {
        mEditor.putString(key, value).commit()
    }


    fun saveInt(key: String, value: Int) {
        mEditor.putInt(key, value).apply()
    }

    fun saveLong(key: String, value: Long) {
        mEditor.putLong(key, value).apply()
    }

    fun saveBoolean(key: String, value: Boolean) {
        mEditor.putBoolean(key, value).apply()
    }

    fun saveBooleanCommit(key: String, value: Boolean) {
        mEditor.putBoolean(key, value).commit()
    }

    fun getString(key: String): String? {
        return mSharedPreferences.getString(key, null)
    }

    fun getString(key: String, defaultValue: String): String? {
        return mSharedPreferences.getString(key, defaultValue)
    }

    fun getInt(key: String): Int {
        return mSharedPreferences.getInt(key, 0)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return mSharedPreferences.getInt(key, defaultValue)
    }

    fun getLong(key: String): Long {
        return mSharedPreferences.getLong(key, 0)
    }

    fun getLong(key: String, defaultValue: Int): Long {
        return mSharedPreferences.getLong(key, defaultValue.toLong())
    }

    fun getBoolean(key: String): Boolean {
        return mSharedPreferences.getBoolean(key, false)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return mSharedPreferences.getBoolean(key, defaultValue)
    }

    fun remove(key: String) {
        mSharedPreferences.edit().remove(key).apply()
    }

    fun clear() {
        mSharedPreferences.edit().clear().apply()
    }
}