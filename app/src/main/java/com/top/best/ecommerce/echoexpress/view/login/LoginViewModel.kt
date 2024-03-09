package com.top.best.ecommerce.echoexpress.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.top.best.ecommerce.echoexpress.core.DataState
import com.top.best.ecommerce.echoexpress.data.AuthRepository
import com.top.best.ecommerce.echoexpress.view.dashboard.seller.profile.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authService: AuthRepository) : ViewModel() {

    private val loginResponse = MutableLiveData<DataState<Profile>>()
    val _loginResponse: LiveData<DataState<Profile>> = loginResponse

    fun userLogin(user: LoginUser) {
        loginResponse.postValue(DataState.Loading())    //loading

        authService.userLogin(user).addOnSuccessListener { it ->
            //loginResponse.postValue(DataState.Success(user))    //success

            checkUserById(it.user?.uid)

        }.addOnFailureListener {
            loginResponse.postValue(DataState.Error(it.message))    //failure
        }

    }

    fun checkUserById(uid: String?) {
        uid?.let { userID ->
            authService.getUserByUserID(userID).addOnSuccessListener { value ->

                loginResponse.postValue(
                    DataState.Success(value.documents.get(0).toObject(Profile::class.java))
                )

            }.addOnFailureListener {
                loginResponse.postValue(DataState.Error(it.message))
            }
        }
    }
}
