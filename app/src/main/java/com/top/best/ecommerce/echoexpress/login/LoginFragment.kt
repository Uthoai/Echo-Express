package com.top.best.ecommerce.echoexpress.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.top.best.ecommerce.echoexpress.R
import com.top.best.ecommerce.echoexpress.databinding.FragmentLoginBinding
import com.top.best.ecommerce.echoexpress.isEmpty

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)

        setListener()

        return binding.root
    }

    private fun setListener() {
        with(binding){
            btnLogin.setOnClickListener {
                etEmail.isEmpty()
                etPassword.isEmpty()
                if (!etEmail.isEmpty() && !etPassword.isEmpty()){
                    checkEmailPasswordValidity()
                }else{
                    Toast.makeText(context, "please fill up email/password", Toast.LENGTH_SHORT).show()
                }
            }
            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }
        }
    }

    private fun checkEmailPasswordValidity() {
        val emailPattern = Regex("^[a-z0-9]+@[a-z]+\\.[a-z]{2,4}$")
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        if (emailPattern.matches(email)){
            if (password.length>=8){
                findNavController().navigate(R.id.action_loginFragment_to_customerDashboardFragment)
            }
            else{
                Toast.makeText(context, "enter correct password", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(context, "enter correct email/password", Toast.LENGTH_SHORT).show()
        }
    }
}
