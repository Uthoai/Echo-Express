package com.top.best.ecommerce.echoexpress.data

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.top.best.ecommerce.echoexpress.core.Nodes
import javax.inject.Inject

class SellerRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageRef: StorageReference,
): SellerSource {
    override fun uploadProductImage(productImageUri: Uri): UploadTask {
        val storage: StorageReference = storageRef.child("products").child("PRO_${System.currentTimeMillis()}")
        return storage.putFile(productImageUri)
    }
    override fun uploadProduct(product: Product): Task<Void> {
        return db.collection(Nodes.PRODUCT).document(product.productID).set(product)
    }

    override fun getAllProductByUserID(userID: String): Task<QuerySnapshot> {
        return db.collection(Nodes.PRODUCT).whereEqualTo("sellerID",userID).get()
    }

}