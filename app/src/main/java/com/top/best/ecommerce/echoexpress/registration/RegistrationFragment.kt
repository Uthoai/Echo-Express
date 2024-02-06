package com.top.best.ecommerce.echoexpress.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.top.best.ecommerce.echoexpress.R
import com.top.best.ecommerce.echoexpress.core.DataState
import com.top.best.ecommerce.echoexpress.databinding.FragmentRegistrationBinding
import com.top.best.ecommerce.echoexpress.isEmpty

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    val viewModel: RegistrationViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater,container,false)

        setListener()
        registrationObserver()

        return binding.root
    }

    private fun registrationObserver() {
        viewModel._registrationResponse.observe(viewLifecycleOwner){
            when(it){
                is DataState.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {
                    Toast.makeText(context, "loading...", Toast.LENGTH_SHORT).show()
                }
                is DataState.Success -> {
                    //Toast.makeText(context, "create user: ${it.data}", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registrationFragment_to_customerDashboardFragment)
                }
            }
        }
    }

    private fun setListener() {
        with(binding){
            btnRegister.setOnClickListener {
                etFullName.isEmpty()
                etEmail.isEmpty()
                etPassword.isEmpty()

                if (!etFullName.isEmpty() && !etEmail.isEmpty() && !etPassword.isEmpty()) {
                    if (etFullName.text.toString().length>=3){
                        checkEmailPasswordValidity()
                    }
                    else{
                        Toast.makeText(context, "please enter proper name", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context, "please fill up all input", Toast.LENGTH_SHORT).show()
                }
            }
            btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            }
        }
    }

    private fun checkEmailPasswordValidity() {
        val emailPattern = Regex("^[a-z0-9]+@[a-z]+\\.[a-z]{2,4}$")
        val name = binding.etFullName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        if (emailPattern.matches(email)){
            if (password.length>=8){
                val user = User(
                    name,
                    email,
                    password,
                    "Seller",
                    ""
                )
                viewModel.userRegistration(user)
            }
            else{
                Toast.makeText(context, "password must be minimum 8 character.", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(context, "enter correct email/password", Toast.LENGTH_SHORT).show()
        }
    }
}