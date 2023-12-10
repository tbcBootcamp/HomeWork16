package com.example.hw16.ui.fragments.welcome

import androidx.navigation.fragment.findNavController
import com.example.hw16.base.BaseFragment
import com.example.hw16.databinding.FragmentWelcomeBinding


class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {

    override fun listeners() {
        binding.apply {
            btnRegister.setOnClickListener {
                findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToRegisterFragment())
            }
            btnLogin.setOnClickListener {
                findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment())
            }
        }
    }

}
