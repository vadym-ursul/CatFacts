package com.dorozhan.catfacts.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Storage @Inject constructor(
    private val prefs: SharedPreferences
) {
    companion object {
        private const val FIRST_TIME_LAUNCH_KEY = "FIRST_TIME_LAUNCH_KEY"
    }

    var isFirstTimeLaunch: Boolean
        get() = prefs.getBoolean(FIRST_TIME_LAUNCH_KEY, true)
        set(passed) {
            prefs.edit().apply {
                putBoolean(FIRST_TIME_LAUNCH_KEY, passed)
            }.apply()
        }
}