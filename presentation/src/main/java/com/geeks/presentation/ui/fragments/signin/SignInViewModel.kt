package com.geeks.presentation.ui.fragments.signin

import androidx.lifecycle.ViewModel
import com.geeks.domain.usecases.CheckSignInUseCase
import com.geeks.domain.usecases.SaveUserDataUseCase
import com.geeks.domain.usecases.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val saveUserDataUseCase: SaveUserDataUseCase,
    private val checkSignInUseCase: CheckSignInUseCase

) : ViewModel() {

    //todo change parameters on object class
    fun signInWithGoogle(idToken: String, onSuccess: () -> Unit, onError: () -> Unit) =
        signInUseCase(idToken, onSuccess, onError)

    fun saveUserData(name: String) = saveUserDataUseCase(name)

    //todo change parameters on object class (selcked )
    fun checkSignIn(email: String, onSuccess: () -> Unit, onError: () -> Unit) =
        checkSignInUseCase(email, onSuccess, onError)
}