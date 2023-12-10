package com.example.hw16.ui.fragments.register

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.hw16.base.BaseFragment
import com.example.hw16.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun listeners() {
        binding.btnRegister.setOnClickListener {
            binding.apply {
                viewModel.register(etEmail.text.toString(), etPassword.text.toString())
            }
        }
    }

    override fun observers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect { state ->
                    state.apply {
                        binding.loader.isVisible = loading
                        if (error.isNotEmpty()) {
                            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                        }
                        registerResponse?.let {
                            Toast.makeText(
                                requireContext(),
                                "Token is:${registerResponse.token}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}