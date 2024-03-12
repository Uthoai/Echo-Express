package com.top.best.ecommerce.echoexpress.view

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.top.best.ecommerce.echoexpress.R
import com.top.best.ecommerce.echoexpress.base.BaseFragment
import com.top.best.ecommerce.echoexpress.core.DataState
import com.top.best.ecommerce.echoexpress.core.Nodes
import com.top.best.ecommerce.echoexpress.view.dashboard.seller.SellerDashboardActivity
import com.top.best.ecommerce.echoexpress.databinding.FragmentStartBinding
import com.top.best.ecommerce.echoexpress.view.dashboard.customer.CustomerDashboardActivity
import com.top.best.ecommerce.echoexpress.view.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding>(FragmentStartBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()
    override fun setListener() {

        setAutoLogin()

        with(binding){
            btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_loginFragment)
            }
            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_registrationFragment)
            }
        }
    }

    private fun setAutoLogin() {

        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if (user != null){
            binding.apply {
                layoutLoading.visibility = View.VISIBLE
                layoutMain.visibility = View.GONE
            }
            user.uid.let {
                viewModel.checkUserById(it)
            }
        } else{
            binding.apply {
                layoutLoading.visibility = View.GONE
                layoutMain.visibility = View.VISIBLE
            }
        }
    }

    override fun allObserver() {
        viewModel._loginResponse.observe(viewLifecycleOwner){
            when(it){
                is DataState.Error -> {
                    loading.dismiss()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {
                    loading.show()
                }
                is DataState.Success -> {
                    loading.dismiss()

                    it.data?.apply {
                        if (userType == Nodes.USER_TYPE_CUSTOMER){
                            startActivity(Intent(requireContext(), CustomerDashboardActivity::class.java))
                            requireActivity().finish()
                        } else if (userType == Nodes.USER_TYPE_SELLER){
                            startActivity(Intent(requireContext(), SellerDashboardActivity::class.java))
                            requireActivity().finish()
                        }
                    }

                }
            }
        }
    }
}
