package com.top.best.ecommerce.echoexpress.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.top.best.ecommerce.echoexpress.login.LoginUser
import com.top.best.ecommerce.echoexpress.registration.User

class AuthService: AuthSource {
    override fun userRegistration(user: User): Task<AuthResult> {
        val mAuth = FirebaseAuth.getInstance()
        return mAuth.createUserWithEmailAndPassword(user.email,user.password)
    }

    override fun userLogin(user: LoginUser): Task<AuthResult> {
        val mAuth = FirebaseAuth.getInstance()
        return mAuth.signInWithEmailAndPassword(user.email,user.password)
    }

    override fun userPassword(email: String) {
        TODO("Not yet implemented")
    }
}