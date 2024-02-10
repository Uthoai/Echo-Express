package com.top.best.ecommerce.echoexpress.registration

import android.provider.ContactsContract.Data
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.top.best.ecommerce.echoexpress.core.DataState
import com.top.best.ecommerce.echoexpress.data.AuthService
import com.top.best.ecommerce.echoexpress.data.AuthSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val authService: AuthService): ViewModel(){

    private val registrationResponse = MutableLiveData<DataState<User>>()
    val _registrationResponse: LiveData<DataState<User>> = registrationResponse

    fun userRegistration(user: User){
        registrationResponse.postValue(DataState.Loading())    //loading

        authService.userRegistration(user).addOnSuccessListener {
            registrationResponse.postValue(DataState.Success(user))    //success
        }.addOnFailureListener {
            registrationResponse.postValue(DataState.Error(it.message))     //failure
        }
    }
}