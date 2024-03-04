package com.top.best.ecommerce.echoexpress.view

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.top.best.ecommerce.echoexpress.R
import com.top.best.ecommerce.echoexpress.base.BaseFragment
import com.top.best.ecommerce.echoexpress.view.dashboard.seller.SellerDashboardActivity
import com.top.best.ecommerce.echoexpress.databinding.FragmentStartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding>(FragmentStartBinding::inflate) {

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
        FirebaseAuth.getInstance().currentUser?.let {
            startActivity(Intent(requireContext(), SellerDashboardActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun allObserver() {

    }
}
