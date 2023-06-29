package com.geeks.data.repositories.preference

import com.geeks.data.preferences.userdata.UserPreferencesData
import com.geeks.domain.repositories.preference.PreferenceRep
import javax.inject.Inject

class PreferenceRepImpl @Inject constructor(
    private val pref: UserPreferencesData
): PreferenceRep {

    override fun getUserName() = pref.userAccountId
}