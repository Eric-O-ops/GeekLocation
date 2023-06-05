package com.geektech.data.repositories.preferences.userdata

import com.geektech.data.repositories.preferences.PreferencesConstants
import com.geektech.data.repositories.preferences.PreferencesHelper

class UserPreferencesData(
    private val preferences: PreferencesHelper
) {

    var userId: Int
        get() = preferences().getInt(
            PreferencesConstants.USER_ID, 0)
        set(value) = preferences()
            .edit().putInt(
                PreferencesConstants.USER_ID, value
            ).apply()

    var userName: String
        get() = preferences().getString(
            PreferencesConstants.USER_NAME, "null"
        ).toString()
        set(value) = preferences()
            .edit().putString(
                PreferencesConstants.USER_NAME, value
            ).apply()
}