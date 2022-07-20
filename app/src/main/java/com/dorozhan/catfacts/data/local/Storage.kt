package com.dorozhan.catfacts.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Storage @Inject constructor(
    private val prefs: SharedPreferences
) {
    companion object {
        private const val ONBOARD_LAUNCH_KEY = "ONBOARD_LAUNCH_KEY"
    }

    var isOnBoardPassed: Boolean
        get() = prefs.getBoolean(ONBOARD_LAUNCH_KEY, false)
        set(passed) {
            prefs.edit().apply {
                putBoolean(ONBOARD_LAUNCH_KEY, passed)
            }.apply()
        }
}