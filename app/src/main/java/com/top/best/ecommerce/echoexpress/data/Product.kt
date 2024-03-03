package com.top.best.ecommerce.echoexpress.data

data class Product(
    var name: String = "",
    var price: Double = 0.0,
    var description: String = "",
    var amount: Int = 0,
    var imageLink: String = "",
    var sellerID: String = "",
    var productID: String = ""
)
