package com.geeks.domain.base.constansts

object Constants {

    object FirebaseUsers {
        const val NAME_COLLECTION = "Users"
        const val USER_ID_FIELD = "id"
        const val USER_ACCOUNT_ID = "accountId"
        const val USER_EMAIL = "email"
        const val USER_NAME_FIELD = "name"
        const val LOC_FIELD = "location"
    }

    object FirebaseCID {
        const val NAME_COLLECTION = "CID"
        const val NAME_DOC = "freeID"
        const val NAME_FIELD = "id"
    }

    object Notification {
        const val FOREGROUND_CONTENT_TEXT = "Приложение отслеживает местоположение"
        const val FOREGROUND_CONTENT_TITLE = "GeekLocation запущено"
    }

    const val SPANNABLE_TEXT_SIGN_IN = "Уже есть аккаунт?\t Войдите"
}