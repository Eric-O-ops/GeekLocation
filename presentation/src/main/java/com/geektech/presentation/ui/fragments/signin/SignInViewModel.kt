package com.geektech.presentation.ui.fragments.signin

import androidx.lifecycle.ViewModel
import com.geektech.domain.usecases.SaveUserDataUseCase
import com.geektech.domain.usecases.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val saveUserDataUseCase: SaveUserDataUseCase

) : ViewModel() {

    fun signInWithGoogle(idToken: String, onSuccess: () -> Unit, onError: () -> Unit) =
        signInUseCase(idToken, onSuccess, onError)

    fun saveUserData(name: String) = saveUserDataUseCase(name)

}