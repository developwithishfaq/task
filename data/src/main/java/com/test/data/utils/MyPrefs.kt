package com.test.data.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MyPrefs @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val prefsEdit = prefs.edit()
    var useRetrofit: Boolean
        get() = prefs.getBoolean("useRetrofit", false)
        set(value) = prefsEdit.putBoolean("useRetrofit", value).apply()

}