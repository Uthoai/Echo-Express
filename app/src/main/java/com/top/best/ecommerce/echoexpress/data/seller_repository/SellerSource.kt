package com.top.best.ecommerce.echoexpress.data.seller_repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.UploadTask
import com.top.best.ecommerce.echoexpress.data.Product
import com.top.best.ecommerce.echoexpress.view.dashboard.seller.profile.Profile

interface SellerSource {
    fun uploadImage(imageUri: Uri,path1: String,path2:String): UploadTask
    fun uploadProduct(product: Product): Task<Void>
    fun getAllDataByUserID(userID: String,collectionPath: String,field: String): Task<QuerySnapshot>
    fun updateProfile(sellerProfile: Profile): Task<Void>

}
