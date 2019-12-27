package com.example.studymanager.account

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.studymanager.R
import com.example.studymanager.databinding.FragmentLoginBinding
import com.example.studymanager.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.NotEmpty

class LoginFragment : Fragment(), Validator.ValidationListener {


    private lateinit var binding: FragmentLoginBinding
    private lateinit var isLoadingProgress: ProgressDialog

    private val viewModel: LoginViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        ViewModelProviders
            .of(this, LoginViewModel.Factory(activity.application))
            .get(LoginViewModel::class.java)
    }

    //Validatie
    private lateinit var validator: Validator

    @NotEmpty(messageResId = R.string.error_empty)
    private lateinit var txtEmail: EditText

    @NotEmpty(messageResId = R.string.error_empty)
    private lateinit var txtWachtwoord: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnRegisterSend.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_loginFragment2_to_registreerFragment2)
        }
        binding.btnLoginSend.setOnClickListener { validator.validate() }

        startObservers()
        registerValidation()

        return binding.root
    }

    private fun registerValidation() {
        this.validator = Validator(this)
        this.validator.setValidationListener(this)

        this.txtEmail = binding.txtLoginEmail.editText!!
        this.txtWachtwoord = binding.txtLoginPassword.editText!!
    }

    private fun startObservers() {
        binding.viewModel?.loginResponse?.observe(this, Observer { httpCode: Int? ->
            if (httpCode != null) {
                when (httpCode) {
                    200 -> {
                        activity?.setResult(Activity.RESULT_OK)
                        activity?.finish()
                    }
                    401 -> Snackbar.make(
                        binding.btnLoginSend,
                        R.string.error_login_failed,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    504 -> Snackbar.make(
                        binding.btnLoginSend,
                        R.string.httperror_504,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    else -> Snackbar.make(
                        binding.btnLoginSend,
                        R.string.httperror_400,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                isLoadingProgress.hide()
            }
        })
    }

    private fun loginUser() {
        val Email = binding.txtLoginEmail.editText?.text.toString()
        val password = binding.txtLoginPassword.editText?.text.toString()

        isLoadingProgress = ProgressDialog(requireContext())
        isLoadingProgress.setMessage(resources.getString(R.string.title_aanmelden))
        isLoadingProgress.setCanceledOnTouchOutside(false)
        isLoadingProgress.setCancelable(false)
        isLoadingProgress.show()

        binding.viewModel?.loginUser(Email, password)
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        for (error in errors!!) {
            val view = error.view
            val message = error.getCollatedErrorMessage(requireContext())

            if (view is EditText) {
                view.error = message
            }
        }
    }

    override fun onValidationSucceeded() {
        loginUser()
    }
}