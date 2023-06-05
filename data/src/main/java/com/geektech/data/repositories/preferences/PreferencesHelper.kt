package com.geektech.data.repositories.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {

    private val preference: SharedPreferences =
        context.getSharedPreferences("userData", Context.MODE_PRIVATE)

    operator fun invoke() = preference
}