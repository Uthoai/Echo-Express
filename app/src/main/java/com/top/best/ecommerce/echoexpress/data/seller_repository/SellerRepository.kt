package com.top.best.ecommerce.echoexpress.data.seller_repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.top.best.ecommerce.echoexpress.core.Nodes
import com.top.best.ecommerce.echoexpress.data.Product
import com.top.best.ecommerce.echoexpress.view.dashboard.seller.profile.Profile
import com.top.best.ecommerce.echoexpress.view.dashboard.seller.profile.toMap
import javax.inject.Inject

class SellerRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageRef: StorageReference,
) : SellerSource {
    override fun uploadImage(imageUri: Uri, path1: String, path2: String): UploadTask {
        val storage: StorageReference =
            storageRef.child(path1).child("$path2${System.currentTimeMillis()}")
        return storage.putFile(imageUri)
    }

    override fun uploadProduct(product: Product): Task<Void> {
        return db.collection(Nodes.PRODUCT).document(product.productID).set(product)
    }

    override fun getAllDataByUserID(userID: String,collectionPath: String,field: String): Task<QuerySnapshot> {
        return db.collection(collectionPath).whereEqualTo(field, userID).get()
    }

    override fun updateProfile(sellerProfile: Profile): Task<Void> {
        return db.collection(Nodes.USER).document(sellerProfile.userID).update(sellerProfile.toMap())
    }

}