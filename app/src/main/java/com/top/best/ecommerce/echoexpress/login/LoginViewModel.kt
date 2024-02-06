package com.top.best.ecommerce.echoexpress.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.top.best.ecommerce.echoexpress.core.DataState
import com.top.best.ecommerce.echoexpress.data.AuthService
import com.top.best.ecommerce.echoexpress.registration.User

class LoginViewModel: ViewModel() {

    private val loginResponse = MutableLiveData<DataState<LoginUser>>()
    val _loginResponse: LiveData<DataState<LoginUser>> = loginResponse

    fun userLogin(user: LoginUser){
        loginResponse.postValue(DataState.Loading())    //loading

        val authService = AuthService()
        authService.userLogin(user).addOnSuccessListener {
            loginResponse.postValue(DataState.Success(user))    //success
        }.addOnFailureListener {
            loginResponse.postValue(DataState.Error(it.message))    //failure
        }
    }
}