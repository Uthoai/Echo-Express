package com.top.best.ecommerce.echoexpress.view.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.top.best.ecommerce.echoexpress.core.DataState
import com.top.best.ecommerce.echoexpress.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val authService: AuthRepository) :
    ViewModel() {

    private val registrationResponse = MutableLiveData<DataState<RegistrationUser>>()
    val _registrationResponse: LiveData<DataState<RegistrationUser>> = registrationResponse

    fun userRegistration(user: RegistrationUser) {
        registrationResponse.postValue(DataState.Loading())    //Auth loading

        authService.userRegistration(user)
            .addOnSuccessListener { it->
                it.user?.let {createdUser->
                    user.userID = createdUser.uid
                    authService.createUser(user)

                        .addOnSuccessListener {
                            registrationResponse.postValue(DataState.Success(user))    //Auth success
                        }.addOnFailureListener {
                            registrationResponse.postValue(DataState.Error(it.message))
                        }
                }
            }.addOnFailureListener {
                registrationResponse.postValue(DataState.Error(it.message))     //Auth failure
            }
    }
}
