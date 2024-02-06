package com.top.best.ecommerce.echoexpress.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.top.best.ecommerce.echoexpress.registration.User

interface AuthSource {
    fun userRegistration(user: User): Task<AuthResult>
    fun userLogin(user: User): Task<AuthResult>
    fun userPassword(email: String)
}
