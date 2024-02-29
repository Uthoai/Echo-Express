package com.top.best.ecommerce.echoexpress.data

data class Product(
    val name: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val amount: Int = 0,
    var imageLink: String = "",
    var sellerID: String = "",
    var productID: String = ""
)
