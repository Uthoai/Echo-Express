package com.top.best.ecommerce.echoexpress.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.top.best.ecommerce.echoexpress.login.LoginUser
import com.top.best.ecommerce.echoexpress.registration.User
import javax.inject.Inject

class AuthService @Inject constructor(private val mAuth: FirebaseAuth): AuthSource {
    override fun userRegistration(user: User): Task<AuthResult> {
        return mAuth.createUserWithEmailAndPassword(user.email,user.password)
    }

    override fun userLogin(user: LoginUser): Task<AuthResult> {
        return mAuth.signInWithEmailAndPassword(user.email,user.password)
    }

    override fun userPassword(email: String) {

    }
}