package com.top.best.ecommerce.echoexpress.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.top.best.ecommerce.echoexpress.view.login.LoginUser
import com.top.best.ecommerce.echoexpress.view.registration.RegistrationUser

interface AuthSource {
    fun userRegistration(user: RegistrationUser): Task<AuthResult>
    fun userLogin(user: LoginUser): Task<AuthResult>
    fun userPassword(email: String)
    fun createUser(user: RegistrationUser): Task<Void>
}
