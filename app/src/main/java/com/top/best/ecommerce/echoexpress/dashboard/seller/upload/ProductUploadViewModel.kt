package com.top.best.ecommerce.echoexpress.dashboard.seller.upload

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.top.best.ecommerce.echoexpress.core.DataState
import com.top.best.ecommerce.echoexpress.data.AuthRepository
import com.top.best.ecommerce.echoexpress.data.Product
import com.top.best.ecommerce.echoexpress.data.SellerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProductUploadViewModel @Inject constructor(private val sellerRepo: SellerRepository): ViewModel() {

    private val productUploadResponse = MutableLiveData<DataState<String>>()
    val _productUploadResponse: LiveData<DataState<String>> = productUploadResponse

    fun productUpload(product: Product){
        productUploadResponse.postValue(DataState.Loading())

        val imageUri: Uri = product.imageLink.toUri()

        sellerRepo.uploadProductImage(imageUri).addOnSuccessListener { taskSnapshot->

            taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri->     //check upload product image to fireStorage
                product.imageLink = uri.toString()

                sellerRepo.uploadProduct(product).addOnSuccessListener {        //check upload product to fireStorage
                    productUploadResponse.postValue(DataState.Success("upload successfully"))
                }.addOnFailureListener {
                    productUploadResponse.postValue(DataState.Error(it.message))
                }
            }

        }.addOnFailureListener {
            productUploadResponse.postValue(DataState.Error(it.message))
        }

    }
}