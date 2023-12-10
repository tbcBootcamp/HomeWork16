package com.example.hw16.ui.fragments.login

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.hw16.base.BaseFragment
import com.example.hw16.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun listeners() {
        binding.btnLogin.setOnClickListener {
            binding.apply {
                viewModel.login(etUsername.text.toString(), etPassword.text.toString())
            }
        }
    }


    override fun observers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect { state ->
                    state.apply {
                        binding.loader.isVisible = loading
                        if (error.isNotEmpty()) {
                            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                        }
                        loginResponse?.let {
                            Toast.makeText(
                                requireContext(),
                                "token is:${loginResponse.token}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}