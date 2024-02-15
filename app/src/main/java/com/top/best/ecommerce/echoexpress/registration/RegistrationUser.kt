package com.top.best.ecommerce.echoexpress.registration

data class RegistrationUser(
    val name: String,
    val email: String,
    val password: String,
    val userType: String,
    var userID: String
)
