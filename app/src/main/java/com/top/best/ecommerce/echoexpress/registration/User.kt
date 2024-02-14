package com.top.best.ecommerce.echoexpress.registration

data class User(
    val name: String,
    val email: String,
    val password: String,
    val userType: String,
    var userID: String
)
