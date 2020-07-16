package com.devper.template.presentation.login

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.core.extension.makeLinks
import com.devper.template.core.platform.BiometricController
import com.devper.template.core.smartlogin.*
import com.devper.template.core.smartlogin.users.SmartUser
import com.devper.template.core.smartlogin.util.SmartLoginException
import com.devper.template.databinding.FragmentLoginBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login),
    SmartLoginCallbacks, BiometricController.Callback {

    private lateinit var config: SmartLoginConfig
    private lateinit var biometric: BiometricController
    private lateinit var smartLogin: SmartLogin

    override val viewModel: LoginViewModel by viewModel()

    override fun setupView() {
        hideToolbar()
        hideBottomNavigation()
        binding.viewModel = viewModel
        binding.tvSignup.makeLinks(Pair(getString(R.string.signup_button), View.OnClickListener {
            viewModel.nextToSignUp()
        }))
        viewModel.clearUser()
        activity?.let {
            config = SmartLoginConfig(it, this)
            biometric = BiometricController(it, this)
            config.facebookAppId = getString(R.string.facebook_app_id)
            config.lineChannelId = "1598367163"
            biometric.authenticate()
        }
    }

    override fun observeLiveData() {
        viewModel.results.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.Success -> {
                    hideDialog()
                    mainViewModel.initProfile()
                    viewModel.nextToOtp()
                }
                is ResultState.Error -> {
                    hideDialog()
                    toError(it.throwable)
                }
            }
        })

        viewModel.login.observe(viewLifecycleOwner, Observer {
            if (it != LoginType.Custom) {
                smartLogin = SmartLoginFactory.build(it).apply {
                    login(config)
                }
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        smartLogin.run {
            showLoading()
            onActivityResult(requestCode, resultCode, data, config)
        }
    }

    override fun onLoginSuccess(user: SmartUser) {
    }

    override fun onLoginFailure(e: SmartLoginException) {
        hideLoading()
        appCompat().showMessage(e.message)
    }

    override fun onAuthenticated() {

    }

    override fun onError() {

    }


}
