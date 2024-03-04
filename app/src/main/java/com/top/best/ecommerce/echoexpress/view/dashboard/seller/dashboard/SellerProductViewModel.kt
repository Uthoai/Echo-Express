package com.top.best.ecommerce.echoexpress.view.dashboard.seller.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.toObject
import com.top.best.ecommerce.echoexpress.core.DataState
import com.top.best.ecommerce.echoexpress.data.Product
import com.top.best.ecommerce.echoexpress.data.SellerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SellerProductViewModel @Inject constructor(private val sellerRepo: SellerRepository): ViewModel() {

    private val productResponse = MutableLiveData<DataState<List<Product>>>()
    val _productResponse: LiveData<DataState<List<Product>>> = productResponse

    fun getAllProductByID(userID: String){
        productResponse.postValue(DataState.Loading())

        sellerRepo.getAllProductByUserID(userID).addOnSuccessListener { document->
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