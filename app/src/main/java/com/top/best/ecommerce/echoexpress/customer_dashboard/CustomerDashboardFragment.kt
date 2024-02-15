package com.top.best.ecommerce.echoexpress.customer_dashboard

import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.top.best.ecommerce.echoexpress.R
import com.top.best.ecommerce.echoexpress.base.BaseFragment
import com.top.best.ecommerce.echoexpress.databinding.FragmentCustomerDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CustomerDashboardFragment : BaseFragment<FragmentCustomerDashboardBinding>
    (FragmentCustomerDashboardBinding::inflate) {

        @Inject
        lateinit var  mAuth: FirebaseAuth
    override fun setListener() {
        with(binding){
            btnLogout.setOnClickListener {
                mAuth.signOut()
                findNavController().navigate(R.id.action_customerDashboardFragment_to_startFragment)
            }
        }
    }

    override fun allObserver() {

    }
}
