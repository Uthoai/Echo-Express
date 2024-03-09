package com.top.best.ecommerce.echoexpress.view.dashboard.seller.profile

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.top.best.ecommerce.echoexpress.core.DataState
import com.top.best.ecommerce.echoexpress.core.Nodes
import com.top.best.ecommerce.echoexpress.data.seller_repository.SellerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SellerProfileViewModel @Inject constructor(private val sellerProfileRepo: SellerRepository): ViewModel() {

    private val profileUpdateResponse = MutableLiveData<DataState<String>>()
    val _profileUpdateResponse: LiveData<DataState<String>> = profileUpdateResponse

    fun updateProfile(sellerProfile: Profile,hasLocalImageUri: Boolean){
        profileUpdateResponse.postValue(DataState.Loading())

        if (hasLocalImageUri){
            val imageUri: Uri? = sellerProfile.userImage?.toUri()

            imageUri?.let {
                sellerProfileRepo.uploadImage(it,"profile","USER").addOnSuccessListener { taskSnapshot->

                    taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri->     //check upload product image to fireStorage
                        sellerProfile.userImage = uri.toString()

                        sellerProfileRepo.updateProfile(sellerProfile).addOnSuccessListener {        //check upload product to fireStorage
                            profileUpdateResponse.postValue(DataState.Success("update done successfully"))
                        }.addOnFailureListener {
                            profileUpdateResponse.postValue(DataState.Error(it.message))
                        }
                    }

                }.addOnFailureListener {
                    profileUpdateResponse.postValue(DataState.Error(it.message))
                }
            }
        }else{
            sellerProfileRepo.updateProfile(sellerProfile).addOnSuccessListener {        //check upload product to fireStorage
                profileUpdateResponse.postValue(DataState.Success("update done successfully"))
            }.addOnFailureListener {
                profileUpdateResponse.postValue(DataState.Error(it.message))
            }
        }


    }

    private val getUserDataResponse = MutableLiveData<DataState<Profile>>()
    val _getUserDataResponse: LiveData<DataState<Profile>> get() = getUserDataResponse

    fun getUserByUserID(userID: String){
        getUserDataResponse.postValue(DataState.Loading())

        sellerProfileRepo.getAllDataByUserID(userID,Nodes.USER,"userID").addOnSuccessListener {value->

            getUserDataResponse.postValue(DataState.Success(value.documents.get(0).toObject(Profile::class.java)))

        }.addOnFailureListener {

        }
    }
}