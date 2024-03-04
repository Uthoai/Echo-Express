package com.top.best.ecommerce.echoexpress.view.dashboard.seller.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.top.best.ecommerce.echoexpress.R
import com.top.best.ecommerce.echoexpress.base.BaseFragment
import com.top.best.ecommerce.echoexpress.core.DataState
import com.top.best.ecommerce.echoexpress.data.Product
import com.top.best.ecommerce.echoexpress.databinding.FragmentSellerHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SellerHomeFragment : BaseFragment<FragmentSellerHomeBinding>(FragmentSellerHomeBinding::inflate) {

    private val viewModel: SellerProductViewModel by viewModels()
    override fun setListener() {
        FirebaseAuth.getInstance()
            .currentUser?.let {
                viewModel.getAllProductByID(it.uid)
            }
    }

    override fun allObserver() {
        viewModel._productResponse.observe(viewLifecycleOwner){
            when(it){
                is DataState.Error -> {
                    loading.dismiss()
                }
                is DataState.Loading -> {
                    loading.show()
                }
                is DataState.Success -> {
                    it.data?.let { it1 -> setDataToRV(it1) }
                    loading.dismiss()
                }
            }
        }
    }

    private fun setDataToRV(it: List<Product>) {
        binding.recyclerViewProduct.adapter = SellerProductAdapter(it)
    }


}
