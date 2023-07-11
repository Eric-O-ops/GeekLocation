package com.geeks.presentation.ui.fragments.signin

import android.app.Activity
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geeks.domain.base.constansts.Constants
import com.geeks.presentation.R
import com.geeks.presentation.base.BaseFragment
import com.geeks.presentation.base.extensions.toast
import com.geeks.presentation.databinding.FragmentSignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment :
    BaseFragment<FragmentSignInBinding, SignInViewModel>(R.layout.fragment_sign_in) {

    @Inject lateinit var gsc: GoogleSignInClient
    @Inject lateinit var auth: FirebaseAuth
    override val binding by viewBinding(FragmentSignInBinding::bind)
    override val viewModel: SignInViewModel by viewModels()

    override fun setupListener() {
        signInListener()
        logIn()
    }

    private fun signInListener() = with(binding) {
        btnGoogle.setOnClickListener {
            etInputName.apply {
                if (this.text.isNotEmpty()) {
                    setBackgroundResource(R.drawable.normal_edit_text)
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    signInLauncher.launch(gsc.signInIntent)
                } else {
                    setBackgroundResource(R.drawable.error_edit_text)
                    setCompoundDrawablesWithIntrinsicBounds(
                        0, 0, R.drawable.ic_priority_high, 0
                    )
                }
            }
        }
    }

    private fun logIn() {

        val spannableString = SpannableString(Constants.SPANNABLE_TEXT_SIGN_IN)
        val spanFColor = ForegroundColorSpan(Color.YELLOW)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                getEmailLauncher.launch(gsc.signInIntent)
            }
        }

        spannableString.apply {
            setSpan(clickableSpan, 19, 26, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            setSpan(spanFColor, 19, 26, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }

        binding.tvCheckSignup.apply {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val token = task.result.idToken
                if (token != null) {
                    viewModel.signInWithGoogle(token,
                        onSuccess = {
                            viewModel.saveUserData(binding.etInputName.text.toString())
                            findNavController().navigate(R.id.action_signInFragment_to_mainFragment)
                        },
                        onError = {
                            toast(R.string.error)
                        })
                } else {
                    Log.e("TokenException", "No Token!")
                }
            }
        }

    private fun isAccountRegistered(email: String) {
        viewModel.checkSignIn(email,
        onSuccess = {
            findNavController().navigate(R.id.action_signInFragment_to_mainFragment)
        },
        onError = {
            toast(R.string.no_account)
            gsc.signOut()
        })
    }

    private val getEmailLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                task.result?.email?.let { email -> isAccountRegistered(email) }
            }
        }
}