package com.mosso.bimbo.login.presentantion.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mosso.bimbo.R
import com.mosso.bimbo.databinding.LoginFragmentBinding
import com.mosso.bimbo.login.presentantion.state.LoginUIState
import com.mosso.bimbo.login.presentantion.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val loginViewModel: LoginViewModel by viewModels()
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnLogin.setOnClickListener {
                loginViewModel.saveUserName(etEmail.text.toString())
            }

            viewLifecycleOwner.lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    loginViewModel.uiState.collect { uiState ->
                        when (uiState) {
                            is LoginUIState.Error -> {
                                Log.d("Mosso",  ""+uiState.message)
                            }
                            LoginUIState.Idle -> {}
                            LoginUIState.OnNextScreen -> {
                                findNavController().navigate(R.id.action_pokemonListFragment_to_pokemonDetailFragment)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}