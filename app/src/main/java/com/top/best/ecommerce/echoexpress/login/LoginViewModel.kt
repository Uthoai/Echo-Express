package com.top.best.ecommerce.echoexpress.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.top.best.ecommerce.echoexpress.core.DataState
import com.top.best.ecommerce.echoexpress.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authService: AuthRepository): ViewModel() {

    private val loginResponse = MutableLiveData<DataState<LoginUser>>()
    val _loginResponse: LiveData<DataState<LoginUser>> = loginResponse

    fun userLogin(user: LoginUser){
        loginResponse.postValue(DataState.Loading())    //loading

        authService.userLogin(user).addOnSuccessListener {
            loginResponse.postValue(DataState.Success(user))    //success
        }.addOnFailureListener {
            loginResponse.postValue(DataState.Error(it.message))    //failure
        }
    }
}