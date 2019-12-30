package com.example.studymanager.account

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.studymanager.R
import com.example.studymanager.databinding.FragmentRegisterBinding
import com.example.studymanager.viewmodels.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Password

private const val PICK_PHOTO_FOR_AVATAR: Int = 1


class RegistreerFragment : Fragment(), Validator.ValidationListener {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var isLoadingProgress: ProgressDialog

    private val viewModel: RegisterViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        ViewModelProviders
            .of(this, RegisterViewModel.Factory(activity.application))
            .get(RegisterViewModel::class.java)
    }


    /*****  VALIDATIE  *****/
    private lateinit var validator: Validator


    @NotEmpty(messageResId = R.string.error_empty)
    @Email(messageResId = R.string.error_invalid_email)
    private lateinit var txtEmail: EditText

    @Password(min = 6, scheme = Password.Scheme.ALPHA, messageResId = R.string.error_invalid_password)
    private lateinit var txtWachtwoord: EditText

    @ConfirmPassword(messageResId = R.string.error_invalid_passwordConfirm)
    private lateinit var txtWachtwoordHerhalen: EditText

    /*****  EINDE VALIDATIE  *****/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        registerListeners()
        startObservers()
        registerValidation()

        return binding.root
    }

    private fun registerValidation() {
        this.validator = Validator(this)
        this.validator.setValidationListener(this)

        this.txtEmail = binding.txtRegisterEmail.editText!!
        this.txtWachtwoord = binding.txtRegisterPassword.editText!!
        this.txtWachtwoordHerhalen = binding.txtRegisterPasswordRepeat.editText!!
    }

    private fun startObservers() {
        binding.viewModel?.registerResponse?.observe(this, Observer { httpCode: Int? ->
            if (httpCode != null) {
                when (httpCode) {
                    200 -> {
                        activity?.setResult(Activity.RESULT_OK)
                        activity?.finish()
                    }
                    400 -> Snackbar.make(
                        binding.btnRegisterSend,
                        R.string.error_register_failed,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    504 -> Snackbar.make(
                        binding.btnRegisterSend,
                        R.string.httperror_504,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    else -> Snackbar.make(
                        binding.btnRegisterSend,
                        R.string.httperror_400,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                isLoadingProgress.hide()
            }
        })
    }

    private fun registerListeners() {
        binding.imgRegisterProfile.setOnClickListener { pickUserPicture() }
        binding.btnRegisterSend.setOnClickListener { this.validator.validate() }
        binding.btnRegistrerenClearPicture.setOnClickListener {
            binding.imgRegisterProfile.setImageResource(R.drawable.ic_add) // mss deze nog aanpassen
            binding.viewModel?.changePicture(null)
        }

    }

    private fun pickUserPicture() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                binding.viewModel?.changePicture(data.data)
                binding.imgRegisterProfile.setImageBitmap(binding.viewModel?.picture)
            }
        }
    }

    private fun registerUser() {
        val Email = binding.txtRegisterEmail.editText?.text.toString()
        val wachtwoord = binding.txtRegisterPassword.editText?.text.toString()
        val wachtwoordHerhaling = binding.txtRegisterPasswordRepeat.editText?.text.toString()

        isLoadingProgress = ProgressDialog(requireContext())
        isLoadingProgress.setMessage(resources.getString(R.string.title_registreren))
        isLoadingProgress.setCanceledOnTouchOutside(false)
        isLoadingProgress.setCancelable(false)
        isLoadingProgress.show()

        binding.viewModel?.registerUser(Email, wachtwoord, wachtwoordHerhaling)
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
        registerUser()
    }

}