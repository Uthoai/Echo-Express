package com.top.best.ecommerce.echoexpress.view.dashboard.customer.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.top.best.ecommerce.echoexpress.R
import com.top.best.ecommerce.echoexpress.base.BaseFragment
import com.top.best.ecommerce.echoexpress.core.DataState
import com.top.best.ecommerce.echoexpress.data.Product
import com.top.best.ecommerce.echoexpress.databinding.FragmentCustomerHomeBinding
import com.top.best.ecommerce.echoexpress.view.dashboard.seller.dashboard.SellerProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerHomeFragment : BaseFragment<FragmentCustomerHomeBinding>(FragmentCustomerHomeBinding::inflate) {
    private val viewModel : CustomerHomeViewModel by viewModels()
    override fun setListener() {

    }

    override fun allObserver() {
        viewModel._productResponse.observe(viewLifecycleOwner){
            when(it){
                is DataState.Error -> {
                    loading.dismiss()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
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
        binding.recyclerViewProduct.adapter = CustomerProductAdapter(it)
    }
}