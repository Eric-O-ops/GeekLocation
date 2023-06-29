package com.geeks.data.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {

    private val preference: SharedPreferences =
        context.getSharedPreferences(PreferencesConstants.NAME_SHARE_PREF, Context.MODE_PRIVATE)

    operator fun invoke() = preference
}