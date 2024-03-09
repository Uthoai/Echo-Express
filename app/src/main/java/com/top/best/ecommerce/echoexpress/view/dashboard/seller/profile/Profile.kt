package com.top.best.ecommerce.echoexpress.view.dashboard.seller.profile


data class Profile(
    var name: String = "",
    var email: String = "",
    val password: String = "",
    var userType: String = "",
    var userID: String = "",
    var userImage: String? = null,
    var shopName: String = ""
)

fun Profile.toMap(): Map<String, Any?>{
    return mapOf(
        "name" to name,
        "email" to email,
        "password" to password,
        "userType" to userType,
        "userID" to userID,
        "userImage" to userImage,
        "shopName" to shopName
    )
}
