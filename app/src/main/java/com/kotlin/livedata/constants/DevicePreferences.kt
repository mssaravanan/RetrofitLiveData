package com.kotlin.livedata.constants

import android.content.Context
import android.content.SharedPreferences

object DevicePreferences {

    /**
     * To get the shared preferences object for the application
     *
     * @param context context of the application
     * @return returns shared preferences object for the application
     */
    operator fun get(context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Add String key value pair to shared preference
     *
     * @param context context of the application
     * @param key     key to be added
     * @param value   string value to be stored for given key
     */
    fun addKey(context: Context, key: String, value: String) {
        val settings = get(context)
        val editor = settings.edit()
        editor.putString(key, value)
        editor.commit()
    }

    /**
     * Get String value for the given key
     *
     * @param context context of the application
     * @param key     key to get its value
     * @return returns string value for given key
     */
    fun getString(context: Context, key: String): String? {
        val prefs = DevicePreferences[context]
        return prefs.getString(key, null)
    }

    fun getStringDecrypted(context: Context, key: String): String? {
        /*SharedPreferences prefs = DevicePreferences.get(context);
        String value = prefs.getString(key, null);
        Crypt crypt = Crypt.getInstance(context);
        return crypt.decrypt(value);*/
        val prefs = DevicePreferences[context]
        return prefs.getString(key, null)
    }

    /**
     * Add Integer key value pair to shared preference
     *
     * @param context context of the application
     * @param key     key to be added
     * @param value   integer value to be stored for given key
     */
    fun addKey(context: Context, key: String, value: Int) {
        val settings = DevicePreferences[context]
        val editor = settings.edit()
        editor.putInt(key, value)
        editor.commit()
    }

    fun addKeyEncrypted(context: Context, key: String, value: String) {
        /*SharedPreferences settings = DevicePreferences.get(context);
        SharedPreferences.Editor editor = settings.edit();
        Crypt crypt = Crypt.getInstance(context);
        editor.putString(key, crypt.encrypt(value));
        editor.commit();*/
        val settings = DevicePreferences[context]
        val editor = settings.edit()
        editor.putString(key, value)
        editor.commit()
    }

    /**
     * Get Integer value for the given key
     *
     * @param context context of the application
     * @param key     key to get its value
     * @return returns integer value for given key
     */
    fun getInt(context: Context, key: String): Int {
        val prefs = DevicePreferences[context]
        return prefs.getInt(key, 0)
    }

    /**
     * To check if the String key exists or not
     *
     * @param mContext context of the application
     * @param key      key to check for existence
     * @param data     empty string to specify the key belongs to string data
     * @return returns true if the key exists, else false
     */
    fun isContainKey(mContext: Context, key: String): Boolean {
        val prefs = DevicePreferences[mContext]
        return prefs.contains(key)
    }

    /**
     * To check if the Integer key exists or not
     *
     * @param mContext context of the application
     * @param key      key to check for existence
     * @return returns true if the key exists, else false
     */
    fun isKeyExists(mContext: Context, key: String): Boolean {
        val prefs = DevicePreferences[mContext]
        val value = prefs.getInt(key, 0)
        return if (value == 0) {
            false
        } else {
            true
        }
    }

    /**
     * Add Boolean key value pair to shared preference
     *
     * @param context context of the application
     * @param key     key to be added
     * @param value   boolean value to be stored for given key
     */
    fun addKey(context: Context, key: String, value: Boolean?) {
        val settings = DevicePreferences[context]
        val editor = settings.edit()
        editor.putBoolean(key, value!!)
        editor.commit()
    }

    /**
     * Get Boolean value for the given key
     *
     * @param context context of the application
     * @param key     key to get its value
     * @return returns boolean value for given key
     */
    fun getBoolean(context: Context, key: String,
                   defValue: Boolean): Boolean {
        val prefs = DevicePreferences[context]
        return prefs.getBoolean(key, defValue)
    }

    fun clearPref(context: Context) {
        val settings = DevicePreferences[context]
        val editor = settings.edit()
        editor.clear()
        editor.apply()
    }


}
