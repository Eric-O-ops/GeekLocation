package com.geektech.data.repositories.preference

import com.geektech.data.preferences.userdata.UserPreferencesData
import com.geektech.domain.repositories.preference.PreferenceRep
import javax.inject.Inject

class PreferenceRepImpl @Inject constructor(
    private val pref: UserPreferencesData
): PreferenceRep {

    override fun getUserName() = pref.userAccountId
}