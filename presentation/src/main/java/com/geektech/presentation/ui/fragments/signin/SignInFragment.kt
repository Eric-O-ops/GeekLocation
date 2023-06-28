package com.geektech.presentation.ui.fragments.signin

import android.app.Activity
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.domain.base.constansts.Constants
import com.geektech.presentation.R
import com.geektech.presentation.base.BaseFragment
import com.geektech.presentation.databinding.FragmentSignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment :
    BaseFragment<FragmentSignInBinding, SignInViewModel>(R.layout.fragment_sign_in) {

    @Inject
    lateinit var gsc: GoogleSignInClient
    @Inject
    lateinit var auth: FirebaseAuth
    override val binding by viewBinding(FragmentSignInBinding::bind)
    override val viewModel: SignInViewModel by viewModels()

    override fun setupListener() {
        signInListener()
        spannableTextListener()
    }

    private fun signInListener() {
        with(binding) {
            btnGoogle.setOnClickListener {
                if (etInputName.text.isNotEmpty()) {
                    etInputName.apply {
                        setBackgroundResource(R.drawable.normal_edit_text)
                        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    }
                    resultLauncher.launch(gsc.signInIntent)
                } else {
                    etInputName.apply {
                        setBackgroundResource(R.drawable.error_edit_text)
                        setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_priority_high,
                            0
                        )
                    }
                }
            }
        }
    }

    private val checkSignUp =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val token = task.result.idToken
                token?.let {
                    viewModel.checkSignIn(it,
                        onSuccess = {
                            requireActivity().apply {
                                finish()
                                startActivity(this.intent)
                            }
                        },
                        onError = {
                            Toast.makeText(
                                requireContext(),
                                R.string.no_account,
                                Toast.LENGTH_SHORT
                            ).show()
                            auth.signOut()
                        })
                }
            }
        }

    private fun checkSingUp() = checkSignUp.launch(gsc.signInIntent)

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val token = task.result.idToken
                if (token != null) {
                    viewModel.signInWithGoogle(token,
                        onSuccess = {
                            viewModel.saveUserData(binding.etInputName.text.toString())
                            requireActivity().apply {
                                finish()
                                startActivity(this.intent)
                            }
                        },
                        onError = {
                            Toast.makeText(
                                requireContext(),
                                Constants.TEXT_ERROR, Toast.LENGTH_SHORT
                            ).show()
                        })
                } else {
                    Log.e("TokenException", "No Token!")
                }
            }
        }

    private fun spannableTextListener() {

        val spannableString = SpannableString(Constants.SPANNABLE_TEXT_SIGN_IN)
        val spanFColor = ForegroundColorSpan(Color.YELLOW)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                checkSingUp()
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
}