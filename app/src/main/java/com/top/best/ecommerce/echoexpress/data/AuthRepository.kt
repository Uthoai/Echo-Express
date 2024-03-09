package com.top.best.ecommerce.echoexpress.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.top.best.ecommerce.echoexpress.core.Nodes
import com.top.best.ecommerce.echoexpress.view.login.LoginUser
import com.top.best.ecommerce.echoexpress.view.registration.RegistrationUser
import javax.inject.Inject

class AuthRepository @Inject constructor(private val mAuth: FirebaseAuth,private val db: FirebaseFirestore)
    : AuthSource {
    override fun userRegistration(user: RegistrationUser): Task<AuthResult> {
        return mAuth.createUserWithEmailAndPassword(user.email,user.password)
    }

    override fun userLogin(user: LoginUser): Task<AuthResult> {
        return mAuth.signInWithEmailAndPassword(user.email,user.password)
    }

    override fun userPassword(email: String) {

    }

    override fun createUser(user: RegistrationUser): Task<Void> {
        return db.collection(Nodes.USER).document(user.userID).set(user)
    }

    fun getUserByUserID(userID: String): Task<QuerySnapshot> {
        return db.collection(Nodes.USER).whereEqualTo("userID", userID).get()
    }
}
