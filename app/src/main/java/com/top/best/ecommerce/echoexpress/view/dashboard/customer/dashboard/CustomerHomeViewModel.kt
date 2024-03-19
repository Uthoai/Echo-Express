package com.top.best.ecommerce.echoexpress.view.dashboard.customer.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.top.best.ecommerce.echoexpress.core.DataState
import com.top.best.ecommerce.echoexpress.data.Product
import com.top.best.ecommerce.echoexpress.data.customer_repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class CustomerHomeViewModel @Inject constructor(private val customerRepo: CustomerRepository): ViewModel() {

    private val productResponse = MutableLiveData<DataState<List<Product>>>()
    val _productResponse: LiveData<DataState<List<Product>>> = productResponse

    init {
        getAllProduct()
    }

    private fun getAllProduct(){
        productResponse.postValue(DataState.Loading())

        customerRepo.getAllProduct().addOnSuccessListener { document->
            val productList = mutableListOf<Product>()

            document.documents.forEach { doc->
                doc.toObject(Product::class.java)?.let {
                    productList.add(it)
                }
            }

            productResponse.postValue(DataState.Success(productList))

        }.addOnFailureListener {
            productResponse.postValue(DataState.Error(it.message))
        }

    }
}