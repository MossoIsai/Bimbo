package com.mosso.bimbo.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mosso.bimbo.databinding.LoginFragmentBinding

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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}